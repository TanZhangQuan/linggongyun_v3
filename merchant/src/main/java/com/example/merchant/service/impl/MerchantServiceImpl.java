package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.sms.SenSMS;
import com.example.common.util.JsonUtils;
import com.example.common.util.MD5;
import com.example.common.util.ReturnJson;
import com.example.common.util.VerificationCheck;
import com.example.merchant.dto.platform.CompanyDto;
import com.example.merchant.dto.platform.CompanyTaxDto;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.CompanyLadderServiceService;
import com.example.merchant.service.HomePageService;
import com.example.merchant.service.MerchantService;
import com.example.merchant.service.TaskService;
import com.example.merchant.util.AcquireID;
import com.example.merchant.util.JwtUtils;
import com.example.merchant.vo.platform.HomePageVO;
import com.example.merchant.vo.merchant.HomePageMerchantVO;
import com.example.merchant.vo.merchant.MerchantInfoVO;
import com.example.merchant.vo.merchant.TaxVO;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.MerchantInfoPo;
import com.example.mybatis.po.MerchantPaymentListPO;
import com.example.mybatis.po.TaxPO;
import com.example.mybatis.vo.BuyerVo;
import com.example.redis.dao.RedisDao;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private MerchantRoleDao merchantRoleDao;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private CompanyInfoDao companyInfoDao;

    @Autowired
    private TaxDao taxDao;

    @Autowired
    private CompanyTaxDao companyTaxDao;

    @Autowired
    private CompanyInvoiceInfoDao companyInvoiceInfoDao;

    @Autowired
    private LinkmanDao linkmanDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PaymentOrderDao paymentOrderDao;

    @Autowired
    private PaymentOrderManyDao paymentOrderManyDao;

    @Autowired
    private AcquireID acquireID;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SenSMS senSMS;

    @Autowired
    private HomePageService homePageService;

    @Autowired
    private CompanyLadderServiceService companyLadderServiceService;

    @Autowired
    private PaymentInventoryDao paymentInventoryDao;

    @Autowired
    private ManagersDao managersDao;


    @Value("${PWD_KEY}")
    String PWD_KEY;

    @Value("${TOKEN}")
    private String TOKEN;

    /**
     * 根据用户名和密码进行登录
     *
     * @param username 用户名
     * @param password 密码
     * @param response
     * @return
     */
    @Override
    public ReturnJson merchantLogin(String username, String password, HttpServletResponse response) {
        String encryptPWD = PWD_KEY + MD5.md5(password);
        QueryWrapper<Merchant> merchantQueryWrapper = new QueryWrapper<>();
        merchantQueryWrapper.eq("user_name", username).eq("pass_word", encryptPWD).eq("status", 1);
        Merchant me = merchantDao.selectOne(merchantQueryWrapper);
        if (me == null) {
            return ReturnJson.error("账号或密码有误！");
        }
        SecurityUtils.getSubject().login(new UsernamePasswordToken(username, encryptPWD));//shiro验证身份
        String token = jwtUtils.generateToken(me.getId());
        response.setHeader(TOKEN, token);
        redisDao.set(me.getId(), JsonUtils.objectToJson(me));
        redisDao.setExpire(me.getId(), 60 * 60 * 24 * 7);
        me.setPassWord("");
        return ReturnJson.success(me);
    }

    /**
     * 根据TOKEN获取登录用户的ID
     *
     * @param request
     * @return
     */
    @Override
    public String getId(HttpServletRequest request) {
        String token = request.getHeader(TOKEN);
        Claims claim = jwtUtils.getClaimByToken(token);
        return claim.getSubject();
    }

    /**
     * 发送验证码码
     *
     * @param mobileCode
     * @return
     */
    @Override
    public ReturnJson senSMS(String mobileCode) {
        ReturnJson rj = new ReturnJson();
        Merchant merchant = this.getOne(new QueryWrapper<Merchant>().eq("login_mobile", mobileCode));
        if (merchant == null || merchant.equals(null)) {
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

    /**
     * 根据手机号和验证码进行登录
     *
     * @param loginMobile 手机号码
     * @param checkCode   验证码
     * @param resource
     * @return
     */
    @Override
    public ReturnJson loginMobile(String loginMobile, String checkCode, HttpServletResponse resource) {
        String redisCheckCode = redisDao.get(loginMobile);
        if (StringUtils.isBlank(redisCheckCode)) {
            return ReturnJson.error("验证码已过期，请重新获取！");
        } else if (!checkCode.equals(redisCheckCode)) {
            return ReturnJson.error("输入的验证码有误");
        } else {
            redisDao.remove(loginMobile);
            Merchant merchant = this.getOne(new QueryWrapper<Merchant>().eq("login_mobile", loginMobile).eq("audit_status", 1));
            merchant.setPassWord("");
            merchant.setPayPwd("");
            String token = jwtUtils.generateToken(merchant.getId());
            resource.setHeader(TOKEN, token);
            redisDao.set(merchant.getId(), JsonUtils.objectToJson(merchant));
            redisDao.setExpire(merchant.getId(), 60 * 60 * 24 * 7);
            return ReturnJson.success(merchant);
        }
    }

    /**
     * 获取商户信息
     *
     * @param merchantId
     * @return
     */
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
        merchantInfoVO.setTaxVOList(taxVOS);
        try {
            merchantInfoVO.setSalesManNmae(salesMan.getRealName());
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

    /**
     * 修改密码或忘记密码
     *
     * @param loginMobile
     * @param checkCode
     * @param newPassWord
     * @return
     */
    @Override
    public ReturnJson updataPassWord(String loginMobile, String checkCode, String newPassWord) {
        String redisCode = redisDao.get(loginMobile);
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

    /**
     * 购买方信息
     *
     * @param id
     * @return
     */
    @Override
    public ReturnJson getBuyerById(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        BuyerVo buyerVo = merchantDao.getBuyerById(id);
        if (buyerVo != null) {
            returnJson = new ReturnJson("查询成功", buyerVo, 200);
        }
        return returnJson;
    }

    /**
     * 根据merchantId获取权限信息
     *
     * @param merchantId
     * @return
     */
    private MerchantRole getMerchantRole(String merchantId) {
        QueryWrapper<MerchantRole> merchantRoleQueryWrapper = new QueryWrapper<MerchantRole>();
        merchantRoleQueryWrapper.eq("merchant_id", merchantId);
        return merchantRoleDao.selectOne(merchantRoleQueryWrapper);
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


    /**
     * 获取的商户
     *
     * @param managersId
     * @param merchantId
     * @param merchantName
     * @param linkMobile
     * @param auditStatus
     * @return
     */
    @Override
    public ReturnJson getMerchantList(String managersId, String merchantId, String merchantName, String linkMobile, Integer auditStatus, Integer page, Integer pageSize) throws CommonException {
        List<String> merchantIds = acquireID.getMerchantIds(managersId);
        Page<MerchantInfoPo> merchantPage = new Page<>(page, pageSize);
        IPage<MerchantInfoPo> merchantInfoPoIPage = merchantDao.selectMerchantInfoPo(merchantPage, merchantIds, merchantId, merchantName, linkMobile, auditStatus);
        ReturnJson returnJson = new ReturnJson();
        returnJson.setCode(200);
        returnJson.setState("success");
        returnJson.setFinished(true);
        returnJson.setPageSize(pageSize);
        returnJson.setItemsCount((int) merchantInfoPoIPage.getTotal());
        returnJson.setPageCount((int) merchantInfoPoIPage.getPages());
        returnJson.setData(merchantInfoPoIPage.getRecords());
        return returnJson;
    }

    /**
     * 删除商户
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson removeMerchant(String merchantId) {
        CompanyInfo companyInfo = companyInfoDao.selectById(merchantId);
        if (companyInfo.getAuditStatus() == 0) {
            companyInfoDao.deleteById(merchantId);
            return ReturnJson.success("删除成功！");
        }
        List<Task> tasks = taskService.list(new QueryWrapper<Task>().eq("company_id", merchantId));
        List<PaymentOrder> paymentOrders = paymentOrderDao.selectList(new QueryWrapper<PaymentOrder>().eq("company_id", merchantId));
        List<PaymentOrderMany> paymentOrderManies = paymentOrderManyDao.selectList(new QueryWrapper<PaymentOrderMany>().eq("company_id", merchantId));
        if (VerificationCheck.listIsNull(tasks) && VerificationCheck.listIsNull(paymentOrders) && VerificationCheck.listIsNull(paymentOrderManies)) {
            companyInfoDao.deleteById(merchantId);
            return ReturnJson.success("删除成功！");
        }
        return ReturnJson.error("该商户做过业务，只能停用该用户！");
    }


    /**
     * 审核商户
     *
     * @param merchantId
     * @return
     */
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

    /**
     * 获取商户的支付流水
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson merchantInfoPaas(String merchantId) {
        ReturnJson returnJson = homePageService.getHomePageInof(merchantId);
        HomePageVO homePageVO = new HomePageVO();
        HomePageMerchantVO homePageMerchantVO = (HomePageMerchantVO) returnJson.getObj();
        BeanUtils.copyProperties(homePageMerchantVO,homePageVO);
        Merchant merchant = merchantDao.selectById(merchantId);
        Integer taxTotal = companyTaxDao.selectCount(new QueryWrapper<CompanyTax>().eq("company_id", merchant.getCompanyId()));
        homePageVO.setTaxTotal(taxTotal);
        ReturnJson merchantPaymentList = this.getMerchantPaymentList(merchantId, 1, 10);
        List data = (List) merchantPaymentList.getData();
        returnJson.setData(data);
        return returnJson;
    }

    /**
     * 获取商户的支付列表
     *
     * @param merchantId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson getMerchantPaymentList(String merchantId, Integer page, Integer pageSize) {
        Page<MerchantPaymentListPO> paymentListPage = new Page(page, pageSize);
        IPage<MerchantPaymentListPO> merchantPaymentListIPage = merchantDao.selectMerchantPaymentList(paymentListPage, merchantId);
        return ReturnJson.success(merchantPaymentListIPage);
    }

    /**
     * 获取支付详情
     *
     * @param paymentOrderId
     * @param packgeStatus
     * @return
     */
    @Override
    public ReturnJson getMerchantPaymentInfo(String paymentOrderId, Integer packgeStatus) {
        ReturnJson returnJson = this.getMerchantPaymentInventory(paymentOrderId, 1, 10);
        if (packgeStatus == 0) {
            PaymentOrder paymentOrder = paymentOrderDao.selectById(paymentOrderId);
            returnJson.setObj(paymentOrder);
        } else {
            PaymentOrderMany paymentOrderMany = paymentOrderManyDao.selectById(paymentOrderId);
            returnJson.setObj(paymentOrderMany);
        }
        return returnJson;
    }

    /**
     * 获取支付清单
     *
     * @param paymentOrderId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson getMerchantPaymentInventory(String paymentOrderId, Integer page, Integer pageSize) {
        Page<PaymentInventory> paymentInventoryPage = new Page<>(page, pageSize);
        Page<PaymentInventory> paymentInventoryPages = paymentInventoryDao.selectPage(paymentInventoryPage, new QueryWrapper<PaymentInventory>().eq("payment_order_id", paymentOrderId));
        return ReturnJson.success(paymentInventoryPages);
    }

    /**
     * 添加商户
     *
     * @param companyDto
     * @return
     * @throws CommonException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson addMerchant(CompanyDto companyDto) throws CommonException {
        CompanyInfo companyInfo = new CompanyInfo();
        BeanUtils.copyProperties(companyDto, companyInfo);

        CompanyInvoiceInfo companyInvoiceInfo = new CompanyInvoiceInfo();
        BeanUtils.copyProperties(companyDto.getCompanyInvoiceInfo(), companyInvoiceInfo);

        companyInfo.setBankName(companyInvoiceInfo.getBankName());
        companyInfo.setBankCode(companyInvoiceInfo.getBankCode());
        companyInfo.setTelephones(companyInvoiceInfo.getMobile());
        companyInfo.setCompanyAddress(companyInvoiceInfo.getCompanyAddress());

        companyInfoDao.insert(companyInfo);
        companyInvoiceInfo.setCompanyId(companyInfo.getId());
        companyInvoiceInfoDao.insert(companyInvoiceInfo);
        List<CompanyTaxDto> companyTaxDtos = companyDto.getCompanyTaxDtos();
        for (CompanyTaxDto companyTaxDto : companyTaxDtos) {
            CompanyTax companyTax = new CompanyTax();
            BeanUtils.copyProperties(companyTaxDto, companyTax);
            companyTax.setCompanyId(companyInfo.getId());
            companyTaxDao.insert(companyTax);
            List<CompanyLadderService> companyLadderServices = companyTaxDto.getCompanyLadderServices();
            if (companyLadderServices != null) {
                for (int i = 0; i < companyLadderServices.size(); i++) {
                    if (i != companyLadderServices.size() - 1) {
                        BigDecimal endMoney = companyLadderServices.get(i).getEndMoney();
                        if (endMoney.compareTo(companyLadderServices.get(i).getEndMoney()) < 1) {
                            throw new CommonException(300, "结束金额应该大于起始金额");
                        }
                        BigDecimal startMoney = companyLadderServices.get(i + 1).getStartMoney();
                        if (startMoney.compareTo(endMoney) < 1) {
                            throw new CommonException(300, "上梯度结束金额应小于下梯度起始金额");
                        }
                    }
                    companyLadderServices.get(i).setCompanyTaxId(companyTax.getId());
                }
                companyLadderServiceService.saveBatch(companyLadderServices);
            }
        }
        Linkman linkman = new Linkman();
        BeanUtils.copyProperties(companyDto.getLinkman(), linkman);
        linkman.setCompanyId(companyInfo.getId());
        linkmanDao.insert(linkman);

        Address address = new Address();
        BeanUtils.copyProperties(companyDto.getAddress(), address);
        address.setCompanyId(companyInfo.getId());
        addressDao.insert(address);

        return ReturnJson.success("添加商户成功！");
    }

    /**
     * 退出登录
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson logout(String merchantId) {
        redisDao.remove(merchantId);
        SecurityUtils.getSubject().logout();
        return ReturnJson.success("退出登录成功！");
    }
}
