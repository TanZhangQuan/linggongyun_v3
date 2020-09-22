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
import com.example.merchant.service.HomePageService;
import com.example.merchant.service.MerchantService;
import com.example.merchant.service.TaskService;
import com.example.merchant.util.AcquireMerchantID;
import com.example.merchant.util.JwtUtils;
import com.example.merchant.vo.HomePageVO;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.MerchantInfoPo;
import com.example.mybatis.po.MerchantPaymentListPO;
import com.example.mybatis.po.TaxPO;
import com.example.mybatis.vo.BuyerVo;
import com.example.redis.dao.RedisDao;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
    private MerchantTaxDao merchantTaxDao;

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
    private AcquireMerchantID acquireMerchantID;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SenSMS senSMS;

    @Autowired
    private SalesManDao salesManDao;

    @Autowired
    private HomePageService homePageService;

    @Autowired
    private PaymentInventoryDao paymentInventoryDao;

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
        merchantQueryWrapper.eq("user_name", username).eq("pass_word", encryptPWD).eq("audit_status",1);
        Merchant me = merchantDao.selectOne(merchantQueryWrapper);
        if (me == null) {
            return ReturnJson.error("账号或密码有误！");
        }
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
    public ReturnJson loginMobile(String loginMobile,String checkCode, HttpServletResponse resource) {
        String redisCheckCode = redisDao.get(loginMobile);
        if (StringUtils.isBlank(redisCheckCode)) {
            return ReturnJson.error("验证码已过期，请重新获取！");
        } else if (!checkCode.equals(redisCheckCode)) {
            return ReturnJson.error("输入的验证码有误");
        } else {
            redisDao.remove(loginMobile);
            Merchant merchant = this.getOne(new QueryWrapper<Merchant>().eq("login_mobile", loginMobile).eq("audit_status",1));
            merchant.setPassWord("");
            merchant.setPayPwd("");
            String token = jwtUtils.generateToken(merchant.getId());
            resource.setHeader(TOKEN,token);
            redisDao.set(merchant.getId(), JsonUtils.objectToJson(merchant));
            redisDao.setExpire(merchant.getId(), 60 * 60 * 24 * 7);
            return ReturnJson.success(merchant);
        }
    }

    /**
     * 获取商户信息
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson merchantInfo(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        CompanyInfo companyInfo = companyInfoDao.selectById(merchant.getCompanyId());
        List<TaxPO> taxPOS = taxDao.selectByMerchantId(merchantId);
        List<Linkman> linkmanList = linkmanDao.selectList(new QueryWrapper<Linkman>().eq("merchant_id", merchantId).orderByAsc("is_not"));
        List<Address> addressList = addressDao.selectList(new QueryWrapper<Address>().eq("owner_id", merchantId).orderByAsc("is_not"));
        SalesMan salesMan = salesManDao.selectById(merchant.getSalesManId());
        Address address = null;
        if (merchant.getAgentId() != null || "".equals(merchant.getAgentId())) {
            address = addressDao.selectById(merchant.getAgentId());
        }
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("salesMan", salesMan);
        map.put("address", address);
        map.put("merchant", merchant);
        map.put("companyInfo", companyInfo);
        map.put("taxPOS", taxPOS);
        map.put("linkmanList", linkmanList);
        map.put("addressList", addressList);
        list.add(map);
        return ReturnJson.success(list);
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
        if (list != null && list.size() > 0) {
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
     * @param managersId
     * @param merchantId
     * @param merchantName
     * @param linkMobile
     * @param auditStatus
     * @return
     */
    @Override
    public ReturnJson getMerchantList(String managersId, String merchantId, String merchantName, String linkMobile, Integer auditStatus ,Integer page,Integer pageSize) {
        List<String> merchantIds = acquireMerchantID.getMerchantIds(managersId);
        Page<MerchantInfoPo> merchantPage = new Page<>(page,pageSize);
        IPage<MerchantInfoPo> merchantInfoPoIPage = merchantDao.selectMerchantInfoPo(merchantPage, merchantIds, merchantId, merchantName, linkMobile, auditStatus);
        ReturnJson returnJson = new ReturnJson();
        returnJson.setCode(200);
        returnJson.setState("success");
        returnJson.setFinished(true);
        returnJson.setPageSize(pageSize);
        returnJson.setItemsCount((int) merchantInfoPoIPage.getTotal());
        returnJson.setPageCount((int)merchantInfoPoIPage.getPages());
        returnJson.setData(merchantInfoPoIPage.getRecords());
        return returnJson;
    }

    /**
     * 删除商户
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson removeMerchant(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant.getAuditStatus() == 0) {
            merchantDao.deleteById(merchantId);
            return ReturnJson.success("删除成功！");
        }
        List<Task> tasks = taskService.list(new QueryWrapper<Task>().eq("merchant_id",merchantId));
        List<PaymentOrder> paymentOrders = paymentOrderDao.selectList(new QueryWrapper<PaymentOrder>().eq("merchant_id", merchantId));
        List<PaymentOrderMany> paymentOrderManies = paymentOrderManyDao.selectList(new QueryWrapper<PaymentOrderMany>().eq("merchant_id", merchantId));
        if (VerificationCheck.listIsNull(tasks) && VerificationCheck.listIsNull(paymentOrders) && VerificationCheck.listIsNull(paymentOrderManies) ) {
            merchantDao.deleteById(merchantId);
            return ReturnJson.success("删除成功！");
        }
        return ReturnJson.success("该商户做过业务，只能停用该用户！");
    }


    /**
     * 审核商户
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson auditMerchant(String merchantId) {
        Merchant merchant = new Merchant();
        merchant.setAuditStatus(1);
        merchant.setId(merchantId);
        int i = merchantDao.updateById(merchant);
        if (i == 1) {
            return ReturnJson.success("审核成功！");
        }
        return ReturnJson.error("审核失败！");
    }

    /**
     * 获取商户的支付流水
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson merchantInfoPaas(String merchantId) {
        ReturnJson returnJson = homePageService.getHomePageInof(merchantId);
        HomePageVO homePageVO =(HomePageVO) returnJson.getObj();
        Integer taxTotal = merchantTaxDao.selectCount(new QueryWrapper<MerchantTax>().eq("merchant_id", merchantId));
        homePageVO.setTaxTotal(taxTotal);
        ReturnJson merchantPaymentList = this.getMerchantPaymentList(merchantId, 1, 10);
        List data = (List) merchantPaymentList.getData();
        returnJson.setData(data);
        return returnJson;
    }

    /**
     * 获取商户的支付列表
     * @param merchantId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson getMerchantPaymentList(String merchantId, Integer page, Integer pageSize) {
        Page<MerchantPaymentListPO> paymentListPage = new Page(page,pageSize);
        IPage<MerchantPaymentListPO> merchantPaymentListIPage = merchantDao.selectMerchantPaymentList(paymentListPage, merchantId);
        return ReturnJson.success(merchantPaymentListIPage);
    }

    /**
     * 获取支付详情
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
     * @param paymentOrderId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson getMerchantPaymentInventory(String paymentOrderId, Integer page, Integer pageSize) {
        Page<PaymentInventory> paymentInventoryPage = new Page<>(page,pageSize);
        Page<PaymentInventory> paymentInventoryPages = paymentInventoryDao.selectPage(paymentInventoryPage, new QueryWrapper<PaymentInventory>().eq("payment_order_id", paymentOrderId));
        return ReturnJson.success(paymentInventoryPages);
    }


}
