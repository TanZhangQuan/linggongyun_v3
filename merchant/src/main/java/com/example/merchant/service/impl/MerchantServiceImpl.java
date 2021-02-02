package com.example.merchant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.PackageType;
import com.example.common.enums.PaymentMethod;
import com.example.common.enums.UnionpayBankType;
import com.example.common.sms.SenSMS;
import com.example.common.util.MD5;
import com.example.common.util.ReturnJson;
import com.example.common.util.UnionpayUtil;
import com.example.common.util.VerificationCheck;
import com.example.merchant.config.shiro.CustomizedToken;
import com.example.merchant.dto.platform.*;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.*;
import com.example.merchant.util.AcquireID;
import com.example.merchant.util.JwtUtils;
import com.example.merchant.util.SnowflakeIdWorker;
import com.example.merchant.vo.merchant.HomePageMerchantVO;
import com.example.merchant.vo.merchant.MerchantInfoVO;
import com.example.merchant.vo.merchant.TaxVO;
import com.example.merchant.vo.platform.*;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.MerchantInfoPo;
import com.example.mybatis.po.MerchantPaymentListPO;
import com.example.mybatis.po.TaxPO;
import com.example.mybatis.vo.*;
import com.example.redis.dao.RedisDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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
    private CompanyTaxService companyTaxService;
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
    private ObjectMenuService objectMenuService;
    @Resource
    private AgentDao agentDao;
    @Resource
    private TaxUnionpayService taxUnionpayService;
    @Resource
    private CompanyUnionpayService companyUnionpayService;
    @Resource
    private TaxService taxService;
    @Resource
    private TaxPackageService taxPackageService;
    @Resource
    private InvoiceLadderPriceService invoiceLadderPriceService;
    @Resource
    private CompanyTaxPayMethodService companyTaxPayMethodService;

    @Override
    public ReturnJson merchantLogin(String username, String password, HttpServletResponse response) {
        String encryptPWD = MD5.md5(PWD_KEY + password);
        Subject currentUser = SecurityUtils.getSubject();
        QueryWrapper<Merchant> merchantQueryWrapper = new QueryWrapper<>();
        merchantQueryWrapper.lambda()
                .eq(Merchant::getUserName, username)
                .eq(Merchant::getPassWord, encryptPWD);
        Merchant me = merchantDao.selectOne(merchantQueryWrapper);
        if (me == null) {
            return ReturnJson.error("账号或密码错误");
        }
        if (me.getStatus() == 1) {
            return ReturnJson.error("账号已被禁用");
        }
        CustomizedToken customizedToken = new CustomizedToken(username, encryptPWD, MERCHANT);
        //shiro验证身份
        currentUser.login(customizedToken);
        String token = jwtUtils.generateToken(me.getId());
        response.setHeader(TOKEN, token);
        redisDao.set(me.getId(), token);
        redisDao.setExpire(me.getId(), 1, TimeUnit.DAYS);
        return ReturnJson.success("登录成功", token);
    }

    @Override
    public ReturnJson senSMS(String mobileCode) {
        ReturnJson rj = new ReturnJson();
        Merchant merchant = this.getOne(new QueryWrapper<Merchant>().lambda()
                .eq(Merchant::getLoginMobile, mobileCode));
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
            Merchant merchant = this.getOne(new QueryWrapper<Merchant>().lambda()
                    .eq(Merchant::getLoginMobile, loginMobile)
                    .eq(Merchant::getStatus, 0));
            String token = jwtUtils.generateToken(merchant.getId());
            resource.setHeader(TOKEN, token);
            redisDao.set(merchant.getId(), token);
            redisDao.setExpire(merchant.getId(), 1, TimeUnit.DAYS);
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
        CompanyInvoiceInfo companyInvoiceInfo = companyInvoiceInfoDao.selectOne(
                new QueryWrapper<CompanyInvoiceInfo>().lambda()
                        .eq(CompanyInvoiceInfo::getCompanyId, merchant.getCompanyId()));
        Managers salesMan = managersDao.selectById(companyInfo.getSalesManId());
        Managers agent = managersDao.selectById(companyInfo.getAgentId());
        List<TaxPO> taxPOS = taxDao.selectByMerchantId(merchant.getCompanyId());
        List<TaxVO> taxVOS = new ArrayList<>();
        for (TaxPO taxPO : taxPOS) {
            TaxVO taxVO = new TaxVO();
            BeanUtils.copyProperties(taxPO, taxVO);
            if (taxPO.getChargeStatus() == 1) {
                List<CompanyLadderService> companyLadderServiceList = companyLadderServiceService.list(
                        new QueryWrapper<CompanyLadderService>().lambda()
                                .eq(CompanyLadderService::getCompanyTaxId, taxPO.getCompanyTaxId())
                                .orderByAsc(CompanyLadderService::getStartMoney));
                taxVO.setCompanyLadderServices(companyLadderServiceList);
            }
            taxVOS.add(taxVO);
        }
        List<Linkman> linkmanList = linkmanDao.selectList(new QueryWrapper<Linkman>().lambda()
                .eq(Linkman::getCompanyId, merchant.getCompanyId())
                .orderByAsc(Linkman::getIsNot));
        List<Address> addressList = addressDao.selectList(new QueryWrapper<Address>().lambda()
                .eq(Address::getCompanyId, merchant.getCompanyId())
                .orderByAsc(Address::getIsNot));

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
            merchant.setPassWord(MD5.md5(PWD_KEY + newPassWord));
            boolean flag = this.update(merchant, new QueryWrapper<Merchant>().lambda()
                    .eq(Merchant::getLoginMobile, loginMobile));
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
            merchantDao.delete(new QueryWrapper<Merchant>().lambda().eq(Merchant::getCompanyId, merchantId));
            objectMenuService.remove(new QueryWrapper<ObjectMenu>().lambda().eq(ObjectMenu::getObjectUserId, merchantId));
            addressDao.delete(new QueryWrapper<Address>().lambda().eq(Address::getCompanyId, merchantId));
            linkmanDao.delete(new QueryWrapper<Linkman>().lambda().eq(Linkman::getCompanyId, merchantId));
            return ReturnJson.success("删除成功！");
        }
        List<Task> tasks = taskService.list(new QueryWrapper<Task>().lambda().eq(Task::getMerchantId, merchantId));
        List<PaymentOrder> paymentOrders = paymentOrderDao.selectList(new QueryWrapper<PaymentOrder>().lambda()
                .eq(PaymentOrder::getCompanyId, merchantId));
        List<PaymentOrderMany> paymentOrderManies = paymentOrderManyDao.selectList(new QueryWrapper<PaymentOrderMany>().eq("company_id", merchantId));
        if (VerificationCheck.listIsNull(tasks) && VerificationCheck.listIsNull(paymentOrders) && VerificationCheck.listIsNull(paymentOrderManies)) {
            companyInfoDao.deleteById(merchantId);
            merchantDao.delete(new QueryWrapper<Merchant>().lambda().eq(Merchant::getCompanyId, merchantId));
            objectMenuService.remove(new QueryWrapper<ObjectMenu>().lambda().eq(ObjectMenu::getObjectUserId, merchantId));
            addressDao.delete(new QueryWrapper<Address>().lambda().eq(Address::getCompanyId, merchantId));
            linkmanDao.delete(new QueryWrapper<Linkman>().lambda().eq(Linkman::getCompanyId, merchantId));
            return ReturnJson.success("删除成功！");
        }
        return ReturnJson.error("该商户做过业务，只能停用该用户！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson auditMerchant(String merchantId, String userId) throws CommonException {
        Managers managers = managersDao.selectById(userId);
        if (managers.getUserSign() != 3) {
            return ReturnJson.error("平台管理员才能进行审核");
        }
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setAuditStatus(1);
        companyInfo.setId(merchantId);
        int i = companyInfoDao.updateById(companyInfo);
        if (i == 1) {
            Merchant merchant = merchantDao.selectOne(new QueryWrapper<Merchant>()
                    .lambda().eq(Merchant::getCompanyId, merchantId)
                    .eq(Merchant::getParentId, 0));
            if (merchant == null) {
                throw new CommonException(300, "商户登录账号异常，请稍后再试！");
            }
            merchant.setStatus(0);
            merchantDao.updateById(merchant);
            return ReturnJson.success("审核成功！");
        }
        return ReturnJson.error("审核失败！");
    }

    @Override
    public ReturnJson merchantInfoPaas(String merchantId) {
        Merchant merchant = merchantDao.selectOne(new QueryWrapper<Merchant>().lambda()
                .eq(Merchant::getCompanyId, merchantId)
                .eq(Merchant::getParentId, 0));
        ReturnJson returnJson = homePageService.getHomePageInfo(merchant.getId());
        HomePageVO homePageVO = new HomePageVO();
        HomePageMerchantVO homePageMerchantVO = (HomePageMerchantVO) returnJson.getObj();
        BeanUtils.copyProperties(homePageMerchantVO, homePageVO);
        Integer taxTotal = companyTaxService.count(new QueryWrapper<CompanyTax>().lambda()
                .eq(CompanyTax::getCompanyId, merchant.getCompanyId()));
        homePageVO.setTaxTotal(taxTotal);
        ReturnJson merchantPaymentList = this.getMerchantPaymentList(merchantId, null, 1, 10);
        List data = (List) merchantPaymentList.getData();
        returnJson.setData(data);
        return returnJson;
    }

    @Override
    public ReturnJson queryTaxPackage(String taxId, Integer packageStatus) {

        TaxPackageDetailVO taxPackageDetailVO = new TaxPackageDetailVO();
        TaxPackage taxPackage = taxPackageService.queryTaxPackage(taxId, packageStatus);
        if (taxPackage != null) {
            taxPackageDetailVO.setTaxPrice(taxPackage.getTaxPrice());
            //获取梯度价
            List<InvoiceLadderPriceDetailVO> invoiceLadderPriceDetailVOList = invoiceLadderPriceService.queryInvoiceLadderPriceDetailVOList(taxPackage.getId());
            if (invoiceLadderPriceDetailVOList != null && invoiceLadderPriceDetailVOList.size() > 0) {
                taxPackageDetailVO.setInvoiceLadderPriceDetailVOList(invoiceLadderPriceDetailVOList);
            }
        }

        return ReturnJson.success(taxPackageDetailVO);
    }

    @Override
    public ReturnJson getMerchantPaymentList(String merchantId, String taxId, Integer page, Integer pageSize) {
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant == null) {
            merchant = new Merchant();
            merchant.setCompanyId(merchantId);
        }
        Page<MerchantPaymentListPO> paymentListPage = new Page(page, pageSize);
        IPage<MerchantPaymentListPO> merchantPaymentListIPage;
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

        companyInfo.setPayPwd(MD5.md5(PWD_KEY + companyDto.getAddMerchantDto().getPayPwd()));
        companyInfo.setAddressAndTelephone(companyInvoiceInfo.getAddressAndTelephone());
        companyInfo.setBankAndAccount(companyInvoiceInfo.getBankAndAccount());

        Managers managers = managersDao.selectById(userId);

        //管理员创建直接通过审核
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

        Merchant merchant = merchantDao.selectOne(new QueryWrapper<Merchant>().lambda()
                .eq(Merchant::getUserName, companyDto.getAddMerchantDto().getUserName()));
        if (merchant != null) {
            throw new CommonException(300, "登录账号存在相同的，请修改后重新操作！");
        }
        merchant = merchantDao.selectOne(new QueryWrapper<Merchant>().lambda()
                .eq(Merchant::getLoginMobile, companyDto.getAddMerchantDto().getLoginMobile()));
        if (merchant != null) {
            throw new CommonException(300, "登录时用的手机号存在相同的，请修改后重新操作！");
        }

        merchant = new Merchant();
        BeanUtils.copyProperties(companyDto.getAddMerchantDto(), merchant);
        merchant.setPassWord(MD5.md5(PWD_KEY + companyDto.getAddMerchantDto().getPassWord()));
        merchant.setCompanyId(companyInfo.getId());
        if (managers.getUserSign() != 3) {
            merchant.setStatus(1);
        }
        merchant.setCompanyName(companyInfo.getCompanyName());
        merchant.setRoleName("管理员");
        merchantDao.insert(merchant);
        List<MenuListVO> listVos = menuDao.getMenuList();
        List<ObjectMenu> objectMenuList = new ArrayList<>();
        for (int i = 0; i < listVos.size(); i++) {
            ObjectMenu objectMenu = new ObjectMenu();
            objectMenu.setMenuId(listVos.get(i).getId());
            objectMenu.setObjectUserId(merchant.getId());
            objectMenuList.add(objectMenu);
            if (listVos.get(i).getList() != null && listVos.get(i).getList().size() > 0) {
                for (MenuVO menu : listVos.get(i).getList()) {
                    objectMenu = new ObjectMenu();
                    objectMenu.setMenuId(menu.getId());
                    objectMenu.setObjectUserId(merchant.getId());
                    objectMenuList.add(objectMenu);
                    for (Menu menu1 : menu.getList()) {
                        objectMenu = new ObjectMenu();
                        objectMenu.setMenuId(menu1.getId());
                        objectMenu.setObjectUserId(merchant.getId());
                        objectMenuList.add(objectMenu);
                    }
                }
            }
        }
        objectMenuService.saveBatch(objectMenuList);
        for (CompanyTaxDTO companyTaxDto : companyTaxDTOS) {
            //判断服务商是否存在
            Tax tax = taxDao.selectById(companyTaxDto.getTaxId());
            if (tax == null) {
                throw new CommonException(300, "服务商不存在");
            }

            CompanyTax companyTax = new CompanyTax();
            BeanUtils.copyProperties(companyTaxDto, companyTax);
            companyTax.setCompanyId(companyInfo.getId());
            companyTaxService.save(companyTax);
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
                    if (endMoney.compareTo(companyLadderServices.get(i).getEndMoney()) < 0) {
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

                    //检查服务商银联是否存在或是否关闭
                    TaxUnionpay taxUnionpay = taxUnionpayService.queryTaxUnionpay(companyTaxDto.getTaxId(), unionpayBankType);
                    if (taxUnionpay == null) {
                        throw new CommonException(300, tax.getTaxName() + "服务商未开通" + unionpayBankType.getDesc() + "银联支付");
                    }
                    if (taxUnionpay.getBoolEnable() == null || !taxUnionpay.getBoolEnable()) {
                        throw new CommonException(300, tax.getTaxName() + "服务商" + unionpayBankType.getDesc() + "银联支付未开启");
                    }

                    //保存商户-服务商支付方式
                    PaymentMethod paymentMethod;
                    switch (unionpayBankType) {

                        case SJBK:

                            paymentMethod = PaymentMethod.UNIONSJBK;
                            break;

                        case PABK:

                            paymentMethod = PaymentMethod.UNIONPABK;
                            break;

                        case WSBK:

                            paymentMethod = PaymentMethod.UNIONWSBK;
                            break;

                        case ZSBK:

                            paymentMethod = PaymentMethod.UNIONZSBK;
                            break;

                        default:
                            throw new CommonException(300, "银联支付方式不存在");
                    }

                    PackageType packageType = companyTaxDto.getPackageStatus() == 0 ? PackageType.TOTALPACKAGE : PackageType.CROWDPACKAGE;

                    CompanyTaxPayMethod companyTaxPayMethod = new CompanyTaxPayMethod();
                    companyTaxPayMethod.setCompanyId(companyInfo.getId());
                    companyTaxPayMethod.setTaxId(tax.getId());
                    companyTaxPayMethod.setPackageType(packageType);
                    companyTaxPayMethod.setPaymentMethod(paymentMethod);
                    companyTaxPayMethod.setBoolEnable(true);
                    companyTaxPayMethodService.save(companyTaxPayMethod);

                    //查询商户是否开通子账号
                    CompanyUnionpay companyUnionpay = companyUnionpayService.queryMerchantUnionpay(companyInfo.getId(), taxUnionpay.getId());
                    if (companyUnionpay != null) {
                        continue;
                    }

                    String inBankCode = "";
                    String inBankNo = "";
                    if (UnionpayBankType.SJBK.equals(unionpayBankType)) {
                        inBankCode = companyDto.getInBankCode();
                        inBankNo = companyInfo.getBankCode();
                    }

                    //开通子账号
                    String uuid = SnowflakeIdWorker.getSerialNumber();
                    JSONObject jsonObject = UnionpayUtil.MB010(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), uuid, companyInfo.getTitleOfAccount(), companyInfo.getCreditCode(), inBankCode, inBankNo);
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
    public ReturnJson queryAgent(String userId) {
        List<Agent> list = agentDao.selectList(new QueryWrapper<Agent>().lambda()
                .eq(Agent::getAgentStatus, 0));
        Managers managers = managersDao.selectById(userId);
        // 管理员登录 查询所有可用代理商
        if (managers.getUserSign() == 2) {
            list = agentDao.selectList(new QueryWrapper<Agent>().lambda()
                    .eq(Agent::getAgentStatus, 0)
                    .eq(Agent::getSalesManId, managers.getId()));
        }
        // 代理商登录 查询自己
        if (managers.getUserSign() == 1) {
            list = agentDao.selectList(new QueryWrapper<Agent>().lambda()
                    .eq(Agent::getManagersId, userId));
        }
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

        //获取公司开票信息
        QueryInvoiceInfoVO queryInvoiceInfoVo = new QueryInvoiceInfoVO();
        CompanyInvoiceInfo companyInvoiceInfo = companyInvoiceInfoDao.selectOne(
                new QueryWrapper<CompanyInvoiceInfo>().lambda()
                        .eq(CompanyInvoiceInfo::getCompanyId, companyId));
        BeanUtils.copyProperties(companyInvoiceInfo, queryInvoiceInfoVo);
        companyVo.setQueryInvoiceInfoVo(queryInvoiceInfoVo);

        //获取公司的登录信息
        Merchant merchant = merchantDao.selectOne(new QueryWrapper<Merchant>().lambda()
                .eq(Merchant::getCompanyId, companyId)
                .eq(Merchant::getParentId, 0));
        QueryMerchantInfoVO queryMerchantInfoVo = new QueryMerchantInfoVO();
        BeanUtils.copyProperties(merchant, queryMerchantInfoVo);

        //支付密码密码设置为空不可见
        queryMerchantInfoVo.setPassWord(null);
        queryMerchantInfoVo.setPayPwd(null);
        companyVo.setQueryMerchantInfoVo(queryMerchantInfoVo);

        //业务员与代理商信息
        QueryCooperationInfoVO queryCooperationInfoVo = new QueryCooperationInfoVO();
        queryCooperationInfoVo.setSalesManId(companyInfo.getSalesManId());
        queryCooperationInfoVo.setAgentId(companyInfo.getAgentId());

        //获取业务员
        Managers managers = managersDao.selectById(companyInfo.getSalesManId());
        queryCooperationInfoVo.setSalesManName(managers.getRealName());

        //获取代理商
        if (companyInfo.getAgentId() != null && companyInfo.getAgentId() == "") {
            managers = managersDao.selectById(companyInfo.getAgentId());
            queryCooperationInfoVo.setAgentName(managers.getRealName());
            queryCooperationInfoVo.setCompanyInfoId(companyId);
        }
        companyVo.setQueryCooperationInfoVo(queryCooperationInfoVo);
        List<CooperationInfoVO> cooperationInfoVOList = taxDao.queryCooper(companyId, null);
        //查询服务商银联支付银行和商户银联支付银行
        if (cooperationInfoVOList != null && cooperationInfoVOList.size() > 0) {
            for (CooperationInfoVO cooperationInfoVO : cooperationInfoVOList) {
                //查询服务商银联支付银行
                List<UnionpayBankType> taxUnionpayBankTypeList = taxUnionpayService.queryTaxUnionpayMethod(cooperationInfoVO.getTaxId());
                cooperationInfoVO.setTaxUnionpayBankTypeList(taxUnionpayBankTypeList);
                //查询商户银联支付银行
                List<UnionpayBankType> companyUnionpayBankTypeList = companyUnionpayService.queryCompanyUnionpayMethod(companyId, cooperationInfoVO.getTaxId());
                cooperationInfoVO.setCompanyUnionpayBankTypeList(companyUnionpayBankTypeList);
            }
        }

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

        //获取旧账户号名称
        String titleOfAccount = companyInfo.getTitleOfAccount();
        //获取旧银行卡号
        String bankCode = companyInfo.getBankCode();

        BeanUtils.copyProperties(updateCompanyDto.getUpdateCompanyInfoDto(), companyInfo);
        if (StringUtils.isNotBlank(updateCompanyDto.getUpdateMerchantInfDto().getPayPwd())) {
            companyInfo.setPayPwd(MD5.md5(PWD_KEY + updateCompanyDto.getUpdateMerchantInfDto().getPayPwd()));
        }
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

        //判断是否存在相同的登录账号
        Merchant merchant1 = merchantDao.selectOne(new QueryWrapper<Merchant>().lambda()
                .eq(Merchant::getUserName, merchant.getUserName()));
        if (merchant1 != null) {
            if (!merchant.getId().equals(merchant1.getId())) {
                throw new CommonException(300, "登录账号存在相同的，请修改后重新操作！");
            }
        }

        if (StringUtils.isNotBlank(updateCompanyDto.getUpdateMerchantInfDto().getPassWord())) {
            merchant.setPassWord(MD5.md5(PWD_KEY + updateCompanyDto.getUpdateMerchantInfDto().getPassWord()));
        }
        merchantDao.updateById(merchant);

        //删除所有商户-服务商总包众包合作信息
        companyTaxService.deleteCompanyTaxByCompany(companyInfo.getId());

        List<UpdateCompanyTaxDTO> updateCompanyTaxDTOList = updateCompanyDto.getUpdateCompanyTaxDtoList();
        if (updateCompanyTaxDTOList != null && updateCompanyTaxDTOList.size() > 0) {
            for (UpdateCompanyTaxDTO updateCompanyTaxDTO : updateCompanyTaxDTOList) {
                //判断服务商是否存在
                Tax tax = taxDao.selectById(updateCompanyTaxDTO.getTaxId());
                if (tax == null) {
                    throw new CommonException(300, "服务商不存在");
                }

                CompanyTax companyTax = new CompanyTax();
//                if (StringUtils.isNotBlank(updateCompanyTaxDTO.getId())) {
//                    companyTax = companyTaxService.getById(updateCompanyTaxDTO.getId());
//                    if (companyTax == null) {
//                        throw new CommonException(300, "合作信息错误");
//                    }
//                    BeanUtils.copyProperties(updateCompanyTaxDTO, companyTax);
//                    companyTax.setCompanyId(updateCompanyDto.getUpdateCompanyInfoDto().getId());
//                    companyTaxService.updateById(companyTax);
//                    List<UpdateCompanyLadderServiceDTO> updateCompanyLadderServiceDtoList = updateCompanyTaxDTO.getUpdateCompanyLadderServiceDtoList();
//                    if (updateCompanyLadderServiceDtoList != null) {
//                        int j = 0;
//                        for (UpdateCompanyLadderServiceDTO updateCompanyLadderServiceDto : updateCompanyLadderServiceDtoList) {
//                            if (j != 0) {
//                                BigDecimal endMoney = updateCompanyLadderServiceDtoList.get(j - 1).getEndMoney();
//                                if (updateCompanyLadderServiceDto.getStartMoney().compareTo(endMoney) != 0) {
//                                    throw new CommonException(300, "上梯度结束金额应等于下梯度起始金额");
//                                }
//                            }
//                            if (updateCompanyLadderServiceDto.getId() != null) {
//                                CompanyLadderService companyLadderService = new CompanyLadderService();
//                                BeanUtils.copyProperties(updateCompanyLadderServiceDto, companyLadderService);
//                                companyLadderService.setCompanyTaxId(companyTax.getId());
//                                companyLadderServiceService.updateById(companyLadderService);
//                            }
//                            if (updateCompanyLadderServiceDto.getId() == null) {
//                                CompanyLadderService companyLadderService = new CompanyLadderService();
//                                companyLadderService.setCompanyTaxId(companyTax.getId());
//                                BeanUtils.copyProperties(updateCompanyLadderServiceDto, companyLadderService);
//                                companyLadderServiceService.save(companyLadderService);
//                            }
//                            j++;
//                        }
//                    }
//                } else {
                BeanUtils.copyProperties(updateCompanyTaxDTO, companyTax);
                companyTax.setCompanyId(updateCompanyDto.getUpdateCompanyInfoDto().getId());
                CompanyTax companyTax1 = companyTaxService.getOne(new QueryWrapper<CompanyTax>().lambda()
                        .eq(CompanyTax::getCompanyId, companyTax.getCompanyId())
                        .eq(CompanyTax::getTaxId, companyTax.getTaxId())
                        .eq(CompanyTax::getPackageStatus, companyTax.getPackageStatus()));
                if (companyTax1 != null) {
                    throw new CommonException(300, "同一个商户与同一个服务商在统一合作类型上只能合作一次！");
                }
                companyTaxService.save(companyTax);
                List<UpdateCompanyLadderServiceDTO> updateCompanyLadderServiceDtoList = updateCompanyTaxDTO.getUpdateCompanyLadderServiceDtoList();
                if (updateCompanyLadderServiceDtoList != null) {
                    int j = 0;
                    for (UpdateCompanyLadderServiceDTO updateCompanyLadderServiceDto : updateCompanyLadderServiceDtoList) {
                        if (j != 0) {
                            BigDecimal endMoney = updateCompanyLadderServiceDtoList.get(j - 1).getEndMoney();
                            if (updateCompanyLadderServiceDto.getStartMoney().compareTo(endMoney) != 0) {
                                throw new CommonException(300, "上梯度结束金额应等于下梯度起始金额");
                            }
                        }
                        CompanyLadderService companyLadderService = new CompanyLadderService();
                        BeanUtils.copyProperties(updateCompanyLadderServiceDto, companyLadderService);
                        companyLadderService.setCompanyTaxId(companyTax.getId());
                        companyLadderServiceService.save(companyLadderService);
                        j++;
                    }
                }
//                }

                //设置商户-服务商支付方式全部不启用
                companyTaxPayMethodService.closeCompanyTaxPayMethod(companyInfo.getId(), tax.getId());

                //注册商户对应服务商银联的子账号
                if (updateCompanyTaxDTO.getUnionpayBankTypeList() != null && updateCompanyTaxDTO.getUnionpayBankTypeList().size() > 0) {
                    for (UnionpayBankType unionpayBankType : updateCompanyTaxDTO.getUnionpayBankTypeList()) {

                        //检查服务商银联是否存在或是否关闭
                        TaxUnionpay taxUnionpay = taxUnionpayService.queryTaxUnionpay(updateCompanyTaxDTO.getTaxId(), unionpayBankType);
                        if (taxUnionpay == null) {
                            throw new CommonException(300, tax.getTaxName() + "服务商未开通" + unionpayBankType.getDesc() + "银联支付");
                        }
                        if (taxUnionpay.getBoolEnable() == null || !taxUnionpay.getBoolEnable()) {
                            throw new CommonException(300, tax.getTaxName() + "服务商" + unionpayBankType.getDesc() + "银联支付未开启");
                        }

                        //保存商户-服务商支付方式
                        PaymentMethod paymentMethod;
                        switch (unionpayBankType) {

                            case SJBK:

                                paymentMethod = PaymentMethod.UNIONSJBK;
                                break;

                            case PABK:

                                paymentMethod = PaymentMethod.UNIONPABK;
                                break;

                            case WSBK:

                                paymentMethod = PaymentMethod.UNIONWSBK;
                                break;

                            case ZSBK:

                                paymentMethod = PaymentMethod.UNIONZSBK;
                                break;

                            default:
                                throw new CommonException(300, "银联支付方式不存在");
                        }

                        PackageType packageType = updateCompanyTaxDTO.getPackageStatus() == 0 ? PackageType.TOTALPACKAGE : PackageType.CROWDPACKAGE;

                        CompanyTaxPayMethod companyTaxPayMethod = companyTaxPayMethodService.queryCompanyTaxPayMethod(companyInfo.getId(), tax.getId(), packageType, paymentMethod);
                        if (companyTaxPayMethod != null) {
                            companyTaxPayMethod.setBoolEnable(true);
                            companyTaxPayMethodService.updateById(companyTaxPayMethod);
                        } else {
                            companyTaxPayMethod = new CompanyTaxPayMethod();
                            companyTaxPayMethod.setCompanyId(companyInfo.getId());
                            companyTaxPayMethod.setTaxId(tax.getId());
                            companyTaxPayMethod.setPackageType(packageType);
                            companyTaxPayMethod.setPaymentMethod(paymentMethod);
                            companyTaxPayMethod.setBoolEnable(true);
                            companyTaxPayMethodService.save(companyTaxPayMethod);
                        }

                        //查询商户是否开通子账号
                        CompanyUnionpay companyUnionpay = companyUnionpayService.queryMerchantUnionpay(companyInfo.getId(), taxUnionpay.getId());
                        if (companyUnionpay != null) {

                            if (UnionpayBankType.SJBK.equals(unionpayBankType)) {

                                //检查盛京银行来款银行账号名称是否变动
                                if (!(titleOfAccount.equals(updateCompanyDto.getUpdateCompanyInfoDto().getTitleOfAccount()))) {
                                    //检查原子账号是否存在余额
                                    JSONObject jsonObject = UnionpayUtil.AC081(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), companyUnionpay.getUid());
                                    if (jsonObject == null) {
                                        throw new CommonException(300, "查询子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联余额失败");
                                    }

                                    Boolean boolSuccess = jsonObject.getBoolean("success");
                                    if (boolSuccess == null || !boolSuccess) {
                                        String errMsg = jsonObject.getString("err_msg");
                                        throw new CommonException(300, "查询子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联余额失败: " + errMsg);
                                    }

                                    JSONObject returnValue = jsonObject.getJSONObject("return_value");
                                    String rtnCode = returnValue.getString("rtn_code");
                                    if (!("S00000".equals(rtnCode))) {
                                        String errMsg = returnValue.getString("err_msg");
                                        throw new CommonException(300, "查询子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联余额失败: " + errMsg);
                                    }

                                    //账面余额，单位元
                                    BigDecimal actBal = returnValue.getBigDecimal("act_bal");
                                    if (BigDecimal.ZERO.compareTo(actBal) < 0) {
                                        throw new CommonException(300, "子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联账面尚有余额，不可新建子账号");
                                    }

                                    //开通子账号
                                    String uuid = SnowflakeIdWorker.getSerialNumber();
                                    jsonObject = UnionpayUtil.MB010(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), uuid, companyInfo.getTitleOfAccount(), companyInfo.getCreditCode(), updateCompanyDto.getUpdateCompanyInfoDto().getInBankCode(), companyInfo.getBankCode());
                                    if (jsonObject == null) {
                                        throw new CommonException(300, tax.getTaxName() + "服务商开通" + unionpayBankType.getDesc() + "银联支付注册子账号失败");
                                    }

                                    boolSuccess = jsonObject.getBoolean("success");
                                    if (boolSuccess == null || !boolSuccess) {
                                        String errMsg = jsonObject.getString("err_msg");
                                        throw new CommonException(300, tax.getTaxName() + "服务商开通" + unionpayBankType.getDesc() + "银联支付注册子账号失败: " + errMsg);
                                    }

                                    returnValue = jsonObject.getJSONObject("return_value");
                                    rtnCode = returnValue.getString("rtn_code");
                                    if (!("S00000".equals(rtnCode))) {
                                        String errMsg = returnValue.getString("err_msg");
                                        throw new CommonException(300, tax.getTaxName() + "服务商开通" + unionpayBankType.getDesc() + "银联支付注册子账号失败: " + errMsg);
                                    }

                                    //更改商户银联信息表
                                    companyUnionpay.setUid(uuid);
                                    companyUnionpay.setSubAccountName(returnValue.getString("sub_account_name"));
                                    companyUnionpay.setSubAccountCode(returnValue.getString("sub_account_code"));
                                    companyUnionpayService.updateById(companyUnionpay);

                                }

                                //检查盛京银行来款银行账号是否变动
                                if (!(bankCode.equals(updateCompanyDto.getUpdateCompanyInfoDto().getBankCode()))) {
                                    //修改盛京来款银行账号
                                    JSONObject jsonObject = UnionpayUtil.AC021(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), companyUnionpay.getUid(), updateCompanyDto.getUpdateCompanyInfoDto().getInBankCode(), companyInfo.getBankCode());

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
                                }
                            }

                            continue;
                        }

                        String inBankCode = "";
                        String inBankNo = "";
                        if (UnionpayBankType.SJBK.equals(unionpayBankType)) {
                            inBankCode = updateCompanyDto.getUpdateCompanyInfoDto().getInBankCode();
                            inBankNo = companyInfo.getBankCode();
                        }

                        //开通子账号
                        String uuid = SnowflakeIdWorker.getSerialNumber();
                        JSONObject jsonObject = UnionpayUtil.MB010(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), uuid, companyInfo.getTitleOfAccount(), companyInfo.getCreditCode(), inBankCode, inBankNo);
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
                        companyUnionpay.setSubAccountName(returnValue.getString("sub_account_name"));
                        companyUnionpay.setSubAccountCode(returnValue.getString("sub_account_code"));
                        companyUnionpayService.save(companyUnionpay);
                    }
                }
            }
        }

        return ReturnJson.success("操作成功");
    }

    @Override
    public ReturnJson taxMerchantInfoPaas(String merchantId, String taxId) {
        return taxService.queryCompanyFlowInfo(merchantId, taxId);
    }

    @Override
    public ReturnJson queryMerchantTransactionFlow(String merchantId, Integer pageNo, Integer pageSize) {
        Page<TaxTransactionFlowVO> merchantPage = new Page<>(pageNo, pageSize);
        IPage<TaxTransactionFlowVO> taxTransactionFlowVOS = merchantDao.queryMerchantTransactionFlow(merchantId, merchantPage);
        return ReturnJson.success(taxTransactionFlowVOS);
    }

    @Override
    public ReturnJson queryCompanyTaxInfo(String companyId, String taxId) {
        List<CooperationInfoVO> cooperationInfoVOList = taxDao.queryCooper(companyId, taxId);
        return ReturnJson.success(cooperationInfoVOList);
    }


}
