package com.example.merchant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.UnionpayBankType;
import com.example.common.sms.SenSMS;
import com.example.common.util.*;
import com.example.merchant.config.shiro.CustomizedToken;
import com.example.merchant.dto.platform.*;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.*;
import com.example.merchant.util.AcquireID;
import com.example.merchant.util.JwtUtils;
import com.example.merchant.vo.merchant.HomePageMerchantVO;
import com.example.merchant.vo.merchant.MerchantInfoVO;
import com.example.merchant.vo.merchant.TaxVO;
import com.example.merchant.vo.platform.*;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.MerchantInfoPo;
import com.example.mybatis.po.MerchantPaymentListPO;
import com.example.mybatis.po.TaxPO;
import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.CooperationInfoVO;
import com.example.mybatis.vo.MenuListVO;
import com.example.mybatis.vo.TaxTransactionFlowVO;
import com.example.redis.dao.RedisDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 商户信息
 * 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantDao, Merchant> implements MerchantService {

    private static final String MERCHANT = "merchant";
    @Value("${PWD_KEY}")
    String PWD_KEY;
    @Value("${TOKEN}")
    private String TOKEN;

    @Resource
    private MerchantDao merchantDao;
    @Resource
    private RedisDao redisDao;
    @Resource
    private CompanyInfoDao companyInfoDao;
    @Resource
    private TaxDao taxDao;
    @Resource
    private CompanyTaxDao companyTaxDao;
    @Resource
    private CompanyInvoiceInfoDao companyInvoiceInfoDao;
    @Resource
    private LinkmanDao linkmanDao;
    @Resource
    private AddressDao addressDao;
    @Resource
    private TaskService taskService;
    @Resource
    private PaymentOrderDao paymentOrderDao;
    @Resource
    private PaymentOrderManyDao paymentOrderManyDao;
    @Resource
    private AcquireID acquireID;
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private SenSMS senSMS;
    @Resource
    private HomePageService homePageService;
    @Resource
    private CompanyLadderServiceService companyLadderServiceService;
    @Resource
    private PaymentInventoryDao paymentInventoryDao;
    @Resource
    private ManagersDao managersDao;
    @Resource
    private PaymentOrderService paymentOrderService;
    @Resource
    private PaymentOrderManyService paymentOrderManyService;
    @Resource
    private MenuDao menuDao;
    @Resource
    private ObjectMenuDao objectMenuDao;
    @Resource
    private AgentDao agentDao;
    @Resource
    private TaxUnionpayService taxUnionpayService;
    @Resource
    private CompanyUnionpayService companyUnionpayService;

    @Override
    public ReturnJson merchantLogin(String username, String password, HttpServletResponse response) {
        String encryptPWD = PWD_KEY + MD5.md5(password);
        Subject currentUser = SecurityUtils.getSubject();
        QueryWrapper<Merchant> merchantQueryWrapper = new QueryWrapper<>();
        merchantQueryWrapper.eq("user_name", username).eq("pass_word", encryptPWD);
        Merchant me = merchantDao.selectOne(merchantQueryWrapper);
        if (me == null) {
            throw new AuthenticationException("账号或密码错误");
        }
        if (me.getStatus() == 1) {
            throw new LockedAccountException("账号已被禁用");
        }
        CustomizedToken customizedToken = new CustomizedToken(username, encryptPWD, MERCHANT);
        //shiro验证身份
        currentUser.login(customizedToken);
        String token = jwtUtils.generateToken(me.getId());
        response.setHeader(TOKEN, token);
        redisDao.set(me.getId(), token);
        redisDao.setExpire(me.getId(), 7, TimeUnit.DAYS);
        return ReturnJson.success("登录成功", token);
    }

    @Override
    public ReturnJson senSMS(String mobileCode) {
        ReturnJson rj = new ReturnJson();
        Merchant merchant = this.getOne(new QueryWrapper<Merchant>().eq("login_mobile", mobileCode));
        if (merchant == null) {
            rj.setCode(401);
            rj.setMessage("你还未注册，请先去注册！");
            return rj;
        }
        Map<String, Object> result = senSMS.senSMS(mobileCode);
        if ("000000".equals(result.get("statusCode"))) {
            rj.setCode(200);
            rj.setMessage("验证码发送成功");
            redisDao.set(mobileCode, String.valueOf(result.get("checkCode")));
            redisDao.setExpire(mobileCode, 5 * 60);
        } else if ("160040".equals(result.get("statusCode"))) {
            rj.setCode(300);
            rj.setMessage(String.valueOf(result.get("statusMsg")));
        } else {
            rj.setCode(300);
            rj.setMessage(String.valueOf(result.get("statusMsg")));
        }
        return rj;
    }

    @Override
    public ReturnJson loginMobile(String loginMobile, String checkCode, HttpServletResponse resource) {
        String redisCheckCode = redisDao.get(loginMobile);
        Subject currentUser = SecurityUtils.getSubject();
        if (StringUtils.isBlank(redisCheckCode)) {
            return ReturnJson.error("验证码已过期，请重新获取！");
        } else if (!checkCode.equals(redisCheckCode)) {
            return ReturnJson.error("输入的验证码有误");
        } else {
            redisDao.remove(loginMobile);
            Merchant merchant = this.getOne(new QueryWrapper<Merchant>().eq("login_mobile", loginMobile).eq("status", 0));
            String token = jwtUtils.generateToken(merchant.getId());
            resource.setHeader(TOKEN, token);
            redisDao.set(merchant.getId(), token);
            redisDao.setExpire(merchant.getId(), 7, TimeUnit.DAYS);
            CustomizedToken customizedToken = new CustomizedToken(merchant.getUserName(), merchant.getPassWord(), MERCHANT);
            currentUser.login(customizedToken);//shiro验证身份
            return ReturnJson.success("登录成功", token);
        }
    }

    @Override
    public ReturnJson getMerchantCustomizedInfo(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        merchant.setPassWord(null);
        return ReturnJson.success(merchant);
    }

    @Override
    public ReturnJson merchantInfo(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        CompanyInfo companyInfo = companyInfoDao.selectById(merchant.getCompanyId());
        CompanyInvoiceInfo companyInvoiceInfo = companyInvoiceInfoDao.selectOne(new QueryWrapper<CompanyInvoiceInfo>().eq("company_id", merchant.getCompanyId()));
        Managers salesMan = managersDao.selectById(companyInfo.getSalesManId());
        Managers agent = managersDao.selectById(companyInfo.getAgentId());
        List<TaxPO> taxPOS = taxDao.selectByMerchantId(merchant.getCompanyId());
        List<TaxVO> taxVOS = new ArrayList<>();
        for (TaxPO taxPO : taxPOS) {
            TaxVO taxVO = new TaxVO();
            BeanUtils.copyProperties(taxPO, taxVO);
            if (taxPO.getChargeStatus() == 1) {
                List<CompanyLadderService> companyLadderServiceList = companyLadderServiceService.list(new QueryWrapper<CompanyLadderService>().eq("company_tax_id", taxPO.getCompanyTaxId()).orderByAsc("start_money"));
                taxVO.setCompanyLadderServices(companyLadderServiceList);
            }
            taxVOS.add(taxVO);
        }
        List<Linkman> linkmanList = linkmanDao.selectList(new QueryWrapper<Linkman>().eq("company_id", merchant.getCompanyId()).orderByAsc("is_not"));
        List<Address> addressList = addressDao.selectList(new QueryWrapper<Address>().eq("company_id", merchant.getCompanyId()).orderByAsc("is_not"));

        MerchantInfoVO merchantInfoVO = new MerchantInfoVO();
        BeanUtils.copyProperties(companyInfo, merchantInfoVO);
        BeanUtils.copyProperties(merchant, merchantInfoVO);
        merchantInfoVO.setTaxVoList(taxVOS);
        try {
            merchantInfoVO.setSalesManNmae(salesMan.getRealName());
            merchantInfoVO.setAgentName(agent.getRealName());
            merchantInfoVO.setCompanyInvoiceInfo(companyInvoiceInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            merchantInfoVO.setAgentName(agent.getRealName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            merchantInfoVO.setCompanyInvoiceInfo(companyInvoiceInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        merchantInfoVO.setMerchantId(merchant.getId());
        merchantInfoVO.setLinkmanList(linkmanList);
        merchantInfoVO.setAddressList(addressList);
        return ReturnJson.success(merchantInfoVO);
    }

    @Override
    public ReturnJson updatePassWord(String loginMobile, String checkCode, String newPassWord) {
        String redisCode = redisDao.get(loginMobile);
        if (redisCode == null) {
            return ReturnJson.error("你的验证码已过期请重新发送！");
        }
        if (redisCode.equals(checkCode)) {
            Merchant merchant = new Merchant();
            merchant.setPassWord(PWD_KEY + MD5.md5(newPassWord));
            boolean flag = this.update(merchant, new QueryWrapper<Merchant>().eq("login_mobile", loginMobile));
            if (flag) {
                redisDao.remove(loginMobile);
                return ReturnJson.success("密码修改成功！");
            } else {
                return ReturnJson.success("密码修改失败！");
            }
        }
        return ReturnJson.error("你的验证码有误！");
    }

    @Override
    public ReturnJson updateHeadPortrait(String userId, String headPortrait) {
        Merchant merchant = merchantDao.selectById(userId);
        merchant.setHeadPortrait(headPortrait);
        merchantDao.updateById(merchant);
        return ReturnJson.success("修改成功");
    }

    @Override
    public ReturnJson getBuyerById(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        BuyerVO buyerVo = merchantDao.getBuyerById(id);
        if (buyerVo != null) {
            returnJson = new ReturnJson("查询成功", buyerVo, 200);
        }
        return returnJson;
    }

    @Override
    public Merchant findByID(String id) {
        return merchantDao.findByID(id);
    }

    @Override
    public ReturnJson getIdAndName() {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        List<Merchant> list = merchantDao.getIdAndName();
        if (list != null) {
            returnJson = new ReturnJson("查询成功", list, 200);
        }
        return returnJson;
    }

    @Override
    public String getNameById(String id) {
        return merchantDao.getNameById(id);
    }

    @Override
    public ReturnJson getMerchantList(String managersId, String merchantId, String merchantName, String linkMobile, Integer auditStatus, Integer page, Integer pageSize) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(managersId);
        Page<MerchantInfoPo> merchantPage = new Page<>(page, pageSize);
        IPage<MerchantInfoPo> merchantInfoPoIPage = merchantDao.selectMerchantInfoPo(merchantPage, merchantIds, merchantId, merchantName, linkMobile, auditStatus);
        return ReturnJson.success(merchantInfoPoIPage);
    }

    @Override
    public ReturnJson removeMerchant(String merchantId) {
        CompanyInfo companyInfo = companyInfoDao.selectById(merchantId);
        if (companyInfo.getAuditStatus() == 0) {
            companyInfoDao.deleteById(merchantId);
            return ReturnJson.success("删除成功！");
        }
        List<Task> tasks = taskService.list(new QueryWrapper<Task>().eq("merchant_id", merchantId));
        List<PaymentOrder> paymentOrders = paymentOrderDao.selectList(new QueryWrapper<PaymentOrder>().eq("company_id", merchantId));
        List<PaymentOrderMany> paymentOrderManies = paymentOrderManyDao.selectList(new QueryWrapper<PaymentOrderMany>().eq("company_id", merchantId));
        if (VerificationCheck.listIsNull(tasks) && VerificationCheck.listIsNull(paymentOrders) && VerificationCheck.listIsNull(paymentOrderManies)) {
            companyInfoDao.deleteById(merchantId);
            return ReturnJson.success("删除成功！");
        }
        return ReturnJson.error("该商户做过业务，只能停用该用户！");
    }

    @Override
    public ReturnJson auditMerchant(String merchantId) {
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setAuditStatus(1);
        companyInfo.setId(merchantId);
        int i = companyInfoDao.updateById(companyInfo);
        if (i == 1) {
            return ReturnJson.success("审核成功！");
        }
        return ReturnJson.error("审核失败！");
    }

    @Override
    public ReturnJson merchantInfoPaas(String merchantId) {
        Merchant merchant = merchantDao.selectOne(new QueryWrapper<Merchant>().eq("company_id", merchantId).eq("parent_id", 0));
        ReturnJson returnJson = homePageService.getHomePageInfo(merchant.getId());
        HomePageVO homePageVO = new HomePageVO();
        HomePageMerchantVO homePageMerchantVO = (HomePageMerchantVO) returnJson.getObj();
        BeanUtils.copyProperties(homePageMerchantVO, homePageVO);
        Integer taxTotal = companyTaxDao.selectCount(new QueryWrapper<CompanyTax>().eq("company_id", merchant.getCompanyId()));
        homePageVO.setTaxTotal(taxTotal);
        ReturnJson merchantPaymentList = this.getMerchantPaymentList(merchantId, null, 1, 10);
        List data = (List) merchantPaymentList.getData();
        returnJson.setData(data);
        return returnJson;
    }

    @Override
    public ReturnJson getMerchantPaymentList(String merchantId, String taxId, Integer page, Integer pageSize) {
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant == null) {
            merchant = new Merchant();
            merchant.setCompanyId(merchantId);
        }
        Page<MerchantPaymentListPO> paymentListPage = new Page(page, pageSize);
        IPage<MerchantPaymentListPO> merchantPaymentListIPage = null;
        if (StringUtils.isBlank(taxId)) {
            merchantPaymentListIPage = merchantDao.selectMerchantPaymentList(paymentListPage, merchant.getCompanyId());
        } else {
            merchantPaymentListIPage = merchantDao.selectTaxMerchantPaymentList(paymentListPage, merchant.getCompanyId(), taxId);
        }

        return ReturnJson.success(merchantPaymentListIPage);
    }

    @Override
    public ReturnJson getMerchantPaymentInfo(String paymentOrderId, Integer packgeStatus) {
        ReturnJson returnJson = this.getMerchantPaymentInventory(paymentOrderId, 1, 10);
        if (packgeStatus == 0) {
            ReturnJson paymentOrderInfo = paymentOrderService.getPaymentOrderInfo(paymentOrderId);
            returnJson.setObj(paymentOrderInfo);
        } else {
            ReturnJson paymentOrderManyInfo = paymentOrderManyService.getPaymentOrderManyInfo(paymentOrderId);
            returnJson.setObj(paymentOrderManyInfo);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getMerchantPaymentInventory(String paymentOrderId, Integer page, Integer pageSize) {
        Page<PaymentInventory> paymentInventoryPage = new Page<>(page, pageSize);
        Page<PaymentInventory> paymentInventoryPages = paymentInventoryDao.selectPage(paymentInventoryPage, new QueryWrapper<PaymentInventory>().eq("payment_order_id", paymentOrderId));
        return ReturnJson.success(paymentInventoryPages);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson addMerchant(CompanyDTO companyDto, String userId) throws Exception {
        CompanyInfo companyInfo = new CompanyInfo();
        BeanUtils.copyProperties(companyDto, companyInfo);

        CompanyInvoiceInfo companyInvoiceInfo = new CompanyInvoiceInfo();
        BeanUtils.copyProperties(companyDto.getAddCompanyInvoiceInfoDto(), companyInvoiceInfo);

        companyInfo.setAddressAndTelephone(companyInvoiceInfo.getAddressAndTelephone());
        companyInfo.setBankAndAccount(companyInvoiceInfo.getBankAndAccount());

        Managers managers = managersDao.selectById(userId);
        if (managers.getUserSign() == 3) {
            companyInfo.setAuditStatus(1);
        }
        companyInfoDao.insert(companyInfo);
        companyInvoiceInfo.setCompanyId(companyInfo.getId());
        companyInvoiceInfoDao.insert(companyInvoiceInfo);
        List<CompanyTaxDTO> companyTaxDTOS = companyDto.getCompanyTaxDtos();

        Linkman linkman = new Linkman();
        BeanUtils.copyProperties(companyDto.getAddLinkmanDto(), linkman);
        linkman.setCompanyId(companyInfo.getId());
        linkman.setStatus(0);
        linkmanDao.insert(linkman);

        Address address = new Address();
        BeanUtils.copyProperties(companyDto.getAddressDto(), address);
        address.setCompanyId(companyInfo.getId());
        address.setStatus(0);
        addressDao.insert(address);

        Merchant merchant = merchantDao.selectOne(new QueryWrapper<Merchant>()
                .eq("user_name", companyDto.getAddMerchantDto().getUserName()));
        if (merchant != null) {
            return ReturnJson.success("登录账号存在相同的，请修改后重新操作！");
        }
        merchant = new Merchant();
        BeanUtils.copyProperties(companyDto.getAddMerchantDto(), merchant);
        merchant.setPayPwd(PWD_KEY + MD5.md5(companyDto.getAddMerchantDto().getPayPwd()));
        merchant.setPassWord(PWD_KEY + MD5.md5(companyDto.getAddMerchantDto().getPassWord()));
        merchant.setCompanyId(companyInfo.getId());
        merchant.setCompanyName(companyInfo.getCompanyName());
        merchant.setRoleName("admin");
        merchantDao.insert(merchant);
        List<MenuListVO> listVos = menuDao.getMenuList();
        for (int i = 0; i < listVos.size(); i++) {
            ObjectMenu objectMenu = new ObjectMenu();
            objectMenu.setMenuId(listVos.get(i).getId());
            objectMenu.setObjectUserId(merchant.getId());
            objectMenuDao.insert(objectMenu);
        }

        for (CompanyTaxDTO companyTaxDto : companyTaxDTOS) {
            //判断服务商是否存在
            Tax tax = taxDao.selectById(companyTaxDto.getTaxId());
            if (tax == null) {
                throw new CommonException(300, "服务商不存在");
            }

            CompanyTax companyTax = new CompanyTax();
            BeanUtils.copyProperties(companyTaxDto, companyTax);
            companyTax.setCompanyId(companyInfo.getId());
            companyTaxDao.insert(companyTax);
            List<AddCompanyLadderServiceDTO> companyLadderServiceDtoList = companyTaxDto.getAddCompanyLadderServiceDtoList();
            List<CompanyLadderService> companyLadderServices = new ArrayList<>();
            for (int i = 0; i < companyLadderServiceDtoList.size(); i++) {
                CompanyLadderService companyLadderService = new CompanyLadderService();
                BeanUtils.copyProperties(companyLadderServiceDtoList.get(i), companyLadderService);
                companyLadderServices.add(companyLadderService);
            }

            for (int i = 0; i < companyLadderServices.size(); i++) {
                if (i != companyLadderServices.size() - 1) {
                    BigDecimal endMoney = companyLadderServices.get(i).getEndMoney();
                    if (endMoney.compareTo(companyLadderServices.get(i).getEndMoney()) == -1) {
                        throw new CommonException(300, "结束金额应该大于起始金额");
                    }
                    BigDecimal startMoney = companyLadderServices.get(i + 1).getStartMoney();
                    if (startMoney.compareTo(endMoney) < 0) {
                        throw new CommonException(300, "上梯度结束金额应小于下梯度起始金额");
                    }
                }
                companyLadderServices.get(i).setCompanyTaxId(companyTax.getId());
            }
            companyLadderServiceService.saveBatch(companyLadderServices);

            //注册商户对应服务商银联的子账号
            if (companyTaxDto.getUnionpayBankTypeList() != null && companyTaxDto.getUnionpayBankTypeList().size() > 0) {
                for (UnionpayBankType unionpayBankType : companyTaxDto.getUnionpayBankTypeList()) {
                    //判断盛京银行是否传银行卡号
                    if (UnionpayBankType.SJBK.equals(unionpayBankType)) {
                        if (StringUtils.isBlank(companyTaxDto.getInBankNo())) {
                            throw new CommonException(300, tax.getTaxName() + "服务商盛京银行银联支付需要输入来款银行账号");
                        }
                    } else {
                        companyTaxDto.setInBankNo("");
                    }

                    //检查服务商银联是否存在或是否关闭
                    TaxUnionpay taxUnionpay = taxUnionpayService.queryTaxUnionpay(companyTaxDto.getTaxId(), unionpayBankType);
                    if (taxUnionpay == null) {
                        throw new CommonException(300, tax.getTaxName() + "服务商未开通" + unionpayBankType.getDesc() + "银联支付");
                    }
                    if (taxUnionpay.getBoolEnable() == null || !taxUnionpay.getBoolEnable()) {
                        throw new CommonException(300, tax.getTaxName() + "服务商" + unionpayBankType.getDesc() + "银联支付未开启");
                    }

                    //开通子账号
                    String uuid = UuidUtil.get32UUID();
                    JSONObject jsonObject = UnionpayUtil.MB010(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), uuid, merchant.getCompanyName(), companyInfo.getCreditCode(), companyTaxDto.getInBankNo());
                    if (jsonObject == null) {
                        throw new CommonException(300, tax.getTaxName() + "服务商开通" + unionpayBankType.getDesc() + "银联支付注册子账号失败");
                    }

                    Boolean boolSuccess = jsonObject.getBoolean("success");
                    if (boolSuccess == null || !boolSuccess) {
                        String errMsg = jsonObject.getString("err_msg");
                        throw new CommonException(300, tax.getTaxName() + "服务商开通" + unionpayBankType.getDesc() + "银联支付注册子账号失败: " + errMsg);
                    }

                    JSONObject returnValue = jsonObject.getJSONObject("return_value");
                    String rtnCode = returnValue.getString("rtn_code");
                    if (!("S00000".equals(rtnCode))) {
                        String errMsg = returnValue.getString("err_msg");
                        throw new CommonException(300, tax.getTaxName() + "服务商开通" + unionpayBankType.getDesc() + "银联支付注册子账号失败: " + errMsg);
                    }

                    //新建商户银联信息表
                    CompanyUnionpay companyUnionpay = new CompanyUnionpay();
                    companyUnionpay.setCompanyId(companyInfo.getId());
                    companyUnionpay.setTaxUnionpayId(taxUnionpay.getId());
                    companyUnionpay.setUid(uuid);
                    companyUnionpay.setInBankNo(companyTaxDto.getInBankNo());
                    companyUnionpay.setSubAccountName(returnValue.getString("sub_account_name"));
                    companyUnionpay.setSubAccountCode(returnValue.getString("sub_account_code"));
                    companyUnionpayService.save(companyUnionpay);
                }
            }
        }

        return ReturnJson.success("添加商户成功！");
    }

    @Override
    public ReturnJson logout(String merchantId) {
        redisDao.remove(merchantId);
        SecurityUtils.getSubject().logout();
        return ReturnJson.success("退出登录成功！");
    }

    @Override
    public ReturnJson queryAgent() {
        List<Agent> list = agentDao.selectList(new QueryWrapper<Agent>().eq("agent_status", 0));
        List<AgentVO> agentVOList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            AgentVO agentVo = new AgentVO();
            agentVo.setAgentName(list.get(i).getAgentName());
            agentVo.setId(list.get(i).getManagersId());
            agentVOList.add(agentVo);
        }
        return ReturnJson.success(agentVOList);
    }

    @Override
    public ReturnJson queryCompanyInfoById(String companyId) {
        CompanyVO companyVo = new CompanyVO();
        CompanyInfoVO companyInfoVo = new CompanyInfoVO();
        CompanyInfo companyInfo = companyInfoDao.selectById(companyId);
        BeanUtils.copyProperties(companyInfo, companyInfoVo);
        companyVo.setCompanyInfoVo(companyInfoVo);
        QueryInvoiceInfoVO queryInvoiceInfoVo = new QueryInvoiceInfoVO();
        CompanyInvoiceInfo companyInvoiceInfo = companyInvoiceInfoDao.selectOne(new QueryWrapper<CompanyInvoiceInfo>().eq("company_id", companyId));
        BeanUtils.copyProperties(companyInvoiceInfo, queryInvoiceInfoVo);
        companyVo.setQueryInvoiceInfoVo(queryInvoiceInfoVo);
        Merchant merchant = merchantDao.selectOne(new QueryWrapper<Merchant>().eq("company_id", companyId).eq("parent_id", 0));
        QueryMerchantInfoVO queryMerchantInfoVo = new QueryMerchantInfoVO();
        BeanUtils.copyProperties(merchant, queryMerchantInfoVo);
        queryMerchantInfoVo.setPassWord(null);
        queryMerchantInfoVo.setPayPwd(null);
        companyVo.setQueryMerchantInfoVo(queryMerchantInfoVo);
        QueryCooperationInfoVO queryCooperationInfoVo = new QueryCooperationInfoVO();
        queryCooperationInfoVo.setSalesManId(companyInfo.getSalesManId());
        queryCooperationInfoVo.setAgentId(companyInfo.getAgentId());
        Managers managers = managersDao.selectById(companyInfo.getSalesManId());
        queryCooperationInfoVo.setSalesManName(managers.getRealName());
        if (companyInfo.getAgentId() != null && companyInfo.getAgentId() == "") {
            managers = managersDao.selectById(companyInfo.getAgentId());
            queryCooperationInfoVo.setAgentName(managers.getRealName());
            queryCooperationInfoVo.setCompanyInfoId(companyId);
        }
        companyVo.setQueryCooperationInfoVo(queryCooperationInfoVo);
        List<CooperationInfoVO> cooperationInfoVOList = taxDao.queryCooper(companyId, null);
        companyVo.setCooperationInfoVoList(cooperationInfoVOList);
        return ReturnJson.success(companyVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson updateCompanyInfo(UpdateCompanyDTO updateCompanyDto) throws Exception {
        CompanyInfo companyInfo = companyInfoDao.selectById(updateCompanyDto.getUpdateCompanyInfoDto().getId());
        if (companyInfo == null) {
            throw new CommonException(300, "商户信息不正确！");
        }
        BeanUtils.copyProperties(updateCompanyDto.getUpdateCompanyInfoDto(), companyInfo);
        companyInfo.setAgentId(updateCompanyDto.getUpdateCooperationDto().getAgentId());
        companyInfo.setSalesManId(updateCompanyDto.getUpdateCooperationDto().getSalesManId());
        companyInfoDao.updateById(companyInfo);
        CompanyInvoiceInfo companyInvoiceInfo = companyInvoiceInfoDao.selectById(updateCompanyDto.getUpdetaInvoiceInfoDto().getId());
        BeanUtils.copyProperties(updateCompanyDto.getUpdetaInvoiceInfoDto(), companyInvoiceInfo);
        companyInvoiceInfoDao.updateById(companyInvoiceInfo);
        Merchant merchant = merchantDao.selectById(updateCompanyDto.getUpdateMerchantInfDto().getId());
        if (merchant == null) {
            throw new CommonException(300, "商户账户信息不正确！");
        }
        BeanUtils.copyProperties(updateCompanyDto.getUpdateMerchantInfDto(), merchant);
        if (StringUtils.isNotBlank(updateCompanyDto.getUpdateMerchantInfDto().getPassWord())) {
            merchant.setPassWord(PWD_KEY + MD5.md5(updateCompanyDto.getUpdateMerchantInfDto().getPassWord()));
        }
        if (StringUtils.isNotBlank(updateCompanyDto.getUpdateMerchantInfDto().getPayPwd())) {
            merchant.setPayPwd(PWD_KEY + MD5.md5(updateCompanyDto.getUpdateMerchantInfDto().getPayPwd()));
        }
        merchantDao.updateById(merchant);
        List<UpdateCompanyTaxDTO> updateCompanyTaxDTOList = updateCompanyDto.getUpdateCompanyTaxDtoList();
        for (UpdateCompanyTaxDTO updateCompanyTaxDTO : updateCompanyTaxDTOList) {
            //判断服务商是否存在
            Tax tax = taxDao.selectById(updateCompanyTaxDTO.getTaxId());
            if (tax == null) {
                throw new CommonException(300, "服务商不存在");
            }

            CompanyTax companyTax = new CompanyTax();
            if (StringUtils.isNotBlank(updateCompanyTaxDTO.getId())) {
                companyTax = companyTaxDao.selectById(updateCompanyTaxDTO.getId());
                if (companyTax == null) {
                    throw new CommonException(300, "信息错误");
                }
                BeanUtils.copyProperties(updateCompanyTaxDTO, companyTax);
                companyTax.setCompanyId(updateCompanyDto.getUpdateCompanyInfoDto().getId());
                companyTaxDao.updateById(companyTax);
                List<UpdateCompanyLadderServiceDTO> updateCompanyLadderServiceDtoList = updateCompanyTaxDTO.getUpdateCompanyLadderServiceDtoList();
                if (updateCompanyLadderServiceDtoList != null) {
                    int j = 0;
                    for (UpdateCompanyLadderServiceDTO updateCompanyLadderServiceDto : updateCompanyLadderServiceDtoList) {
                        if (j != 0) {
                            BigDecimal endMoney = updateCompanyLadderServiceDtoList.get(j - 1).getEndMoney();
                            if (updateCompanyLadderServiceDto.getStartMoney().compareTo(endMoney) == -1) {
                                throw new CommonException(300, "上梯度结束金额应小于下梯度起始金额");
                            }
                        }
                        if (updateCompanyLadderServiceDto.getId() != null) {
                            CompanyLadderService companyLadderService = new CompanyLadderService();
                            BeanUtils.copyProperties(updateCompanyLadderServiceDto, companyLadderService);
                            companyLadderService.setCompanyTaxId(companyTax.getId());
                            companyLadderServiceService.updateById(companyLadderService);
                        }
                        if (updateCompanyLadderServiceDto.getId() == null) {
                            CompanyLadderService companyLadderService = new CompanyLadderService();
                            companyLadderService.setCompanyTaxId(companyTax.getId());
                            BeanUtils.copyProperties(updateCompanyLadderServiceDto, companyLadderService);
                            companyLadderServiceService.save(companyLadderService);
                        }
                        j++;
                    }
                }
            } else {
                BeanUtils.copyProperties(updateCompanyTaxDTO, companyTax);
                companyTax.setCompanyId(updateCompanyDto.getUpdateCompanyInfoDto().getId());
                companyTaxDao.insert(companyTax);
                List<UpdateCompanyLadderServiceDTO> updateCompanyLadderServiceDtoList = updateCompanyTaxDTO.getUpdateCompanyLadderServiceDtoList();
                if (updateCompanyLadderServiceDtoList != null) {
                    int j = 0;
                    for (UpdateCompanyLadderServiceDTO updateCompanyLadderServiceDto : updateCompanyLadderServiceDtoList) {
                        if (j != 0) {
                            BigDecimal endMoney = updateCompanyLadderServiceDtoList.get(j - 1).getEndMoney();
                            if (updateCompanyLadderServiceDto.getStartMoney().compareTo(endMoney) < 0) {
                                throw new CommonException(300, "上梯度结束金额应小于下梯度起始金额");
                            }
                        }
                        CompanyLadderService companyLadderService = new CompanyLadderService();
                        BeanUtils.copyProperties(updateCompanyLadderServiceDto, companyLadderService);
                        companyLadderService.setCompanyTaxId(companyTax.getId());
                        companyLadderServiceService.save(companyLadderService);
                        j++;
                    }
                }
            }

            if (updateCompanyTaxDTO.getUnionpayBankTypeList() != null) {
                //注册商户对应服务商银联的子账号
                for (UnionpayBankType unionpayBankType : updateCompanyTaxDTO.getUnionpayBankTypeList()) {
                    //判断盛京银行是否传银行卡号
                    if (UnionpayBankType.SJBK.equals(unionpayBankType)) {
                        if (StringUtils.isBlank(updateCompanyTaxDTO.getInBankNo())) {
                            throw new CommonException(300, tax.getTaxName() + "服务商盛京银行银联支付需要输入来款银行账号");
                        }
                    } else {
                        updateCompanyTaxDTO.setInBankNo("");
                    }

                    //检查服务商银联是否存在或是否关闭
                    TaxUnionpay taxUnionpay = taxUnionpayService.queryTaxUnionpay(updateCompanyTaxDTO.getTaxId(), unionpayBankType);
                    if (taxUnionpay == null) {
                        throw new CommonException(300, tax.getTaxName() + "服务商未开通" + unionpayBankType.getDesc() + "银联支付");
                    }
                    if (taxUnionpay.getBoolEnable() == null || !taxUnionpay.getBoolEnable()) {
                        throw new CommonException(300, tax.getTaxName() + "服务商" + unionpayBankType.getDesc() + "银联支付未开启");
                    }

                    //查询商户是否开通子账号
                    CompanyUnionpay companyUnionpay = companyUnionpayService.queryMerchantUnionpay(companyInfo.getId(), taxUnionpay.getId());
                    if (companyUnionpay != null) {
                        //检查盛京银行来款银行账号是否变动
                        if (UnionpayBankType.SJBK.equals(unionpayBankType) && !(companyUnionpay.getInBankNo().equals(updateCompanyTaxDTO.getInBankNo()))) {
                            //修改盛京来款银行账号
                            JSONObject jsonObject = UnionpayUtil.AC021(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), companyUnionpay.getUid(), updateCompanyTaxDTO.getInBankNo());

                            if (jsonObject == null) {
                                throw new CommonException(300, tax.getTaxName() + "服务商" + unionpayBankType.getDesc() + "银联支付子账户更换绑卡失败");
                            }

                            Boolean boolSuccess = jsonObject.getBoolean("success");
                            if (boolSuccess == null || !boolSuccess) {
                                String errMsg = jsonObject.getString("err_msg");
                                throw new CommonException(300, tax.getTaxName() + "服务商" + unionpayBankType.getDesc() + "银联支付子账户更换绑卡失败: " + errMsg);
                            }

                            JSONObject returnValue = jsonObject.getJSONObject("return_value");
                            String rtnCode = returnValue.getString("rtn_code");
                            if (!("S00000".equals(rtnCode))) {
                                String errMsg = returnValue.getString("err_msg");
                                throw new CommonException(300, tax.getTaxName() + "服务商" + unionpayBankType.getDesc() + "银联支付子账户更换绑卡失败: " + errMsg);
                            }

                            //更新商户-服务商银联记录绑定的来款银行账号
                            companyUnionpay.setInBankNo(updateCompanyTaxDTO.getInBankNo());
                            companyUnionpayService.updateById(companyUnionpay);
                        }
                        continue;
                    }

                    //开通子账号
                    String uuid = UuidUtil.get32UUID();
                    JSONObject jsonObject = UnionpayUtil.MB010(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), uuid, merchant.getCompanyName(), companyInfo.getCreditCode(), updateCompanyTaxDTO.getInBankNo());
                    if (jsonObject == null) {
                        throw new CommonException(300, tax.getTaxName() + "服务商开通" + unionpayBankType.getDesc() + "银联支付注册子账号失败");
                    }

                    Boolean boolSuccess = jsonObject.getBoolean("success");
                    if (boolSuccess == null || !boolSuccess) {
                        String errMsg = jsonObject.getString("err_msg");
                        throw new CommonException(300, tax.getTaxName() + "服务商开通" + unionpayBankType.getDesc() + "银联支付注册子账号失败: " + errMsg);
                    }

                    JSONObject returnValue = jsonObject.getJSONObject("return_value");
                    String rtnCode = returnValue.getString("rtn_code");
                    if (!("S00000".equals(rtnCode))) {
                        String errMsg = returnValue.getString("err_msg");
                        throw new CommonException(300, tax.getTaxName() + "服务商开通" + unionpayBankType.getDesc() + "银联支付注册子账号失败: " + errMsg);
                    }

                    //新建商户银联信息表
                    companyUnionpay = new CompanyUnionpay();
                    companyUnionpay.setCompanyId(companyInfo.getId());
                    companyUnionpay.setTaxUnionpayId(taxUnionpay.getId());
                    companyUnionpay.setUid(uuid);
                    companyUnionpay.setInBankNo(updateCompanyTaxDTO.getInBankNo());
                    companyUnionpay.setSubAccountName(returnValue.getString("sub_account_name"));
                    companyUnionpay.setSubAccountCode(returnValue.getString("sub_account_code"));
                    companyUnionpayService.save(companyUnionpay);
                }
            }
        }

        return ReturnJson.success("操作成功");
    }

    @Override
    public ReturnJson taxMerchantInfoPaas(String merchantId, String taxId) {
        return homePageService.getHomePageInfo(merchantId);
    }

    @Override
    public ReturnJson queryMerchantTransactionFlow(String merchantId, Integer pageNo, Integer pageSize) {
        Page<TaxTransactionFlowVO> merchantPage = new Page<>(pageNo, pageSize);
        List<TaxTransactionFlowVO> taxTransactionFlowVOS = merchantDao.queryMerchantTransactionFlow(merchantId, merchantPage);
        return ReturnJson.success(taxTransactionFlowVOS);
    }

    @Override
    public ReturnJson queryCompanyTaxInfo(String companyId, String taxId) {
        List<CooperationInfoVO> cooperationInfoVOList = taxDao.queryCooper(companyId, taxId);
        return ReturnJson.success(cooperationInfoVOList);
    }


}
