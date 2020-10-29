package com.example.merchant.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.lianlianpay.entity.PaymentRequestBean;
import com.example.common.lianlianpay.entity.QuerySingleOrderRequest;
import com.example.common.lianlianpay.entity.ServicePayApplyRequest;
import com.example.common.lianlianpay.enums.SignTypeEnum;
import com.example.common.lianlianpay.utils.RSAUtil;
import com.example.common.lianlianpay.utils.SignUtil;
import com.example.common.util.JsonUtils;
import com.example.common.util.ReturnJson;
import com.example.common.util.VerificationCheck;
import com.example.merchant.dto.merchant.AddLianLianPay;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.LianLianPayService;
import com.example.merchant.util.RealnameVerifyUtil;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.lianlianpay.security.utils.LianLianPaySecurity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class LianLianPayServiceImpl extends ServiceImpl<LianlianpayDao, Lianlianpay> implements LianLianPayService {


    @Resource
    private MerchantDao merchantDao;

    @Resource
    private TaxPackageDao taxPackageDao;

    @Resource
    private PaymentOrderDao paymentOrderDao;

    @Resource
    private PaymentOrderManyDao paymentOrderManyDao;

    @Resource
    private PaymentInventoryDao paymentInventoryDao;

    @Resource
    private LianlianpayTaxDao lianlianpayTaxDao;

    @Resource
    private WorkerDao workerDao;

    @Resource
    private WorkerBankDao workerBankDao;


    /**
     * 连连公钥
     */
    @Value("${salary.PUBLIC_KEY_ONLINE}")
    private String PUBLIC_KEY_ONLINE;


    /**
     * 接收商户总包付款的连薪回调通知地址
     */
    @Value("${salary.merchantNotifyUrl}")
    private String merchantNotifyUrl;

    /**
     * 接收商户众包付款的连薪回调通知地址
     */
    @Value("${salary.merchantManyNotifyUrl}")
    private String merchantManyNotifyUrl;

    /**
     * 接收付款给创客的连薪回调通知地址
     */
    @Value("${salary.workerNotifyUrl}")
    private String workerNotifyUrl;

    //查询商户余额接口
    @Value("${salary.url.selectRemainingSum}")
    private String selectRemainingSum;

    //实时付款接口
    @Value("${salary.url.paymentapi}")
    private String paymentapi;

    @Value("${salary.url.querySingleOrder}")
    private String querySingleOrder;

    @Override
    public ReturnJson addLianlianPay(String merchantId, AddLianLianPay addLianLianPay) {
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant == null) {
            return ReturnJson.error("您输入的商户不存在！");
        }
        Lianlianpay lianlianpay = new Lianlianpay();
        lianlianpay.setCompanyId(merchant.getCompanyId());
        lianlianpay.setOidPartner(addLianLianPay.getOidPartner());
        lianlianpay.setPrivateKey(addLianLianPay.getPrivateKey());
        boolean flag = this.save(lianlianpay);
        if (flag) {
            return ReturnJson.success("添加成功！");
        }
        return ReturnJson.error("添加失败，请重试！");
    }

    @Override
    public ReturnJson merchantPay(String paymentOrderId) throws CommonException {
        PaymentOrder paymentOrder = paymentOrderDao.selectById(paymentOrderId);
        if (paymentOrder == null) {
            return ReturnJson.error("您输入的订单不存在！");
        }
        if (paymentOrder.getPaymentOrderStatus() > 1) {
            log.error("请勿重复支付");
            return ReturnJson.error("请勿重复支付");
        }
        Lianlianpay lianlianpay = this.getOne(new QueryWrapper<Lianlianpay>().lambda().eq(Lianlianpay::getCompanyId, paymentOrder.getCompanyId()));
        if (lianlianpay == null) {
            log.error("连连账号不存在！");
            return ReturnJson.error("请先添加连连商户号和私钥");
        }

        TaxPackage taxPackage = taxPackageDao.selectOne(new QueryWrapper<TaxPackage>().lambda().eq(TaxPackage::getTaxId, paymentOrder.getTaxId()).eq(TaxPackage::getPackageStatus, 0));
        if (taxPackage == null) {
            log.error("服务商不存在！");
            return ReturnJson.error("您输入的服务商不存在，请联系客服！");
        }

//        Map<String, Object> map = this.selectRemainingSum(lianlianpay.getOidPartner(), lianlianpay.getPrivateKey());
//        String ret_code = map.get("ret_code") == null ? "" : String.valueOf(map.get("ret_code"));
//        BigDecimal money = null;
//        if ("0000".equals(ret_code)) {
//            money = map.get("ret_code") == null ? new BigDecimal(0) : new BigDecimal(String.valueOf(map.get("amt_balance")));
//        } else {
//            log.error(String.valueOf(map.get("ret_msg")));
//            throw new CommonException(Integer.valueOf(ret_code), map.get("ret_msg").toString());
//        }
//
//        if (!(paymentOrder.getRealMoney().compareTo(money) < 0)) {
//            log.error("余额不足！");
//            return ReturnJson.error("余额不足，请先充值！");
//        }

        paymentOrder.setPaymentOrderStatus(4);
        paymentOrderDao.updateById(paymentOrder);
        PaymentRequestBean paymentRequestBean = new PaymentRequestBean();
        paymentRequestBean.setOid_partner(lianlianpay.getOidPartner());
        paymentRequestBean.setApi_version("1.0");
        paymentRequestBean.setNo_order(paymentOrder.getId());
        paymentRequestBean.setDt_order(DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN));
        paymentRequestBean.setMoney_order(paymentOrder.getRealMoney().setScale(2, BigDecimal.ROUND_DOWN).toString());
        paymentRequestBean.setCard_no(taxPackage.getBankCode());
        paymentRequestBean.setAcct_name(taxPackage.getPayee());
        paymentRequestBean.setInfo_order("付款");
        paymentRequestBean.setFlag_card("0");
        paymentRequestBean.setMemo("付款");
        paymentRequestBean.setNotify_url(merchantNotifyUrl);
        paymentRequestBean.setSign_type(SignTypeEnum.RSA.getCode());
        String signData = SignUtil.genSignData(JSON.parseObject((JSON.toJSONString(paymentRequestBean))));
        paymentRequestBean.setSign(RSAUtil.sign(lianlianpay.getPrivateKey(), signData));
        String jsonStr = JSON.toJSONString(paymentRequestBean);
        log.info("实时付款接口请求报文：" + jsonStr);
        // 用银通公钥对请求参数json字符串加密
        // 报Illegal sign key
        // size异常时，可参考这个网页解决问题http://www.wxdl.cn/java/security-invalidkey-exception.html
        String encryptStr = null;
        try {
            encryptStr = LianLianPaySecurity.encrypt(jsonStr, PUBLIC_KEY_ONLINE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //	System.out.print("实时付款接口加密请求报文：" + encryptStr);
        if (StringUtils.isEmpty(encryptStr)) {
            // 加密异常
            log.error("加密失败！");
            paymentOrder.setPaymentOrderStatus(1);
            paymentOrderDao.updateById(paymentOrder);
            return ReturnJson.error("支付失败！");
        }
        JSONObject json = new JSONObject();
        json.put("oid_partner", lianlianpay.getOidPartner());
        json.put("pay_load", encryptStr);
        log.info("实时付款接口请求报文：" + json);
        String res = HttpUtil.post(paymentapi, JSON.toJSONString(json));
        log.info("实时付款接口返回报文：" + res);
        Map<String, String> result = JsonUtils.jsonToPojo(res, new HashMap<String, String>().getClass());
        if ("0000".equals(result.get("ret_code"))) {
            return ReturnJson.success("后台支付中，请稍后查看！");
        } else {
            log.error(result.get("ret_msg"));
            paymentOrder.setPaymentOrderStatus(1);
            paymentOrderDao.updateById(paymentOrder);
            throw new CommonException(Integer.valueOf(result.get("ret_code")), result.get("ret_msg"));
        }
    }

    @Override
    public void merchantNotifyUrl(HttpServletRequest request) {
        String requestBody = null;
        try {
            requestBody = RealnameVerifyUtil.getRequestBody(request, "UTF-8");
            log.info(requestBody);
            HashMap<String, String> result = JsonUtils.jsonToPojo(requestBody, new HashMap<String, String>().getClass());
            log.info(result.toString());
            String panymentOrderId = result.get("no_order");
            PaymentOrder paymentOrder = paymentOrderDao.selectById(panymentOrderId);
            if (paymentOrder == null) {
                log.error("支付订单为空！");
                return;
            }
            if ("SUCCESS".equals(result.get("result_pay"))) {
                paymentOrder.setPaymentOrderStatus(2);
                paymentOrder.setPaymentDate(LocalDateTime.now());
                paymentOrder.setPaymentMode(1);
                paymentOrderDao.updateById(paymentOrder);
                LianlianpayTax lianlianpayTax = lianlianpayTaxDao.selectOne(new QueryWrapper<LianlianpayTax>().lambda().eq(LianlianpayTax::getTaxId, paymentOrder.getTaxId()));
                List<PaymentInventory> paymentInventories = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().lambda().eq(PaymentInventory::getPaymentOrderId, panymentOrderId));

//                //查询余额
//                Map<String, Object> map = this.selectRemainingSum(lianlianpayTax.getOidPartner(), lianlianpayTax.getPrivateKey());
//                String ret_code = map.get("ret_code") == null ? "" : String.valueOf(map.get("ret_code"));
//                BigDecimal money = null;
//                if ("0000".equals(ret_code)) {
//                    money = map.get("ret_code") == null ? new BigDecimal(0) : new BigDecimal(String.valueOf(map.get("amt_balance")));
//                } else {
//                    log.error(String.valueOf(map.get("ret_msg")));
//                }
//                if (!(paymentOrder.getWorkerMoney().compareTo(money) < 0)) {
//                    log.error("余额不足！");
//                    return;
//                }
                //给创客付款
                for (PaymentInventory paymentInventory : paymentInventories) {
                    Worker worker = workerDao.selectById(paymentInventory.getWorkerId());
                    if (!(worker.getAgreementSign() == 2 && worker.getAttestation() == 1)) {
                        log.error("创客未认证" + "\t总包ID:" + panymentOrderId + "\t支付清单ID:" + paymentInventory.getId() + "\t创客姓名:" + worker.getAccountName());
                        paymentInventory.setPaymentStatus(-1);
                        paymentInventoryDao.updateById(paymentInventory);
                        continue;
                    }
                    List<WorkerBank> workerBanks = workerBankDao.selectList(new QueryWrapper<WorkerBank>().lambda().eq(WorkerBank::getWorkerId, worker.getId()));
                    if (VerificationCheck.listIsNull(workerBanks)) {
                        log.error("创客未绑定银行卡" + "\t总包ID:" + panymentOrderId + "\t支付清单ID:" + paymentInventory.getId() + "\t创客姓名:" + worker.getAccountName());
                        paymentInventory.setPaymentStatus(-1);
                        paymentInventoryDao.updateById(paymentInventory);
                        continue;
                    }
                    if (paymentInventory.getPaymentStatus() == 1) {
                        log.error("订单已支付！");
                        continue;
                    }
                    PaymentRequestBean paymentRequestBean = new PaymentRequestBean();
                    paymentRequestBean.setOid_partner(lianlianpayTax.getOidPartner());
                    paymentRequestBean.setApi_version("1.0");
                    paymentRequestBean.setNo_order(paymentInventory.getId());
                    paymentRequestBean.setDt_order(DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN));
                    paymentRequestBean.setMoney_order(paymentInventory.getRealMoney().setScale(2, BigDecimal.ROUND_DOWN).toString());
                    paymentRequestBean.setCard_no(workerBanks.get(0).getBankCode());
                    paymentRequestBean.setAcct_name(worker.getAccountName());
                    paymentRequestBean.setInfo_order("付款");
                    paymentRequestBean.setFlag_card("0");
                    paymentRequestBean.setMemo("付款");
                    paymentRequestBean.setNotify_url(workerNotifyUrl);
                    paymentRequestBean.setSign_type(SignTypeEnum.RSA.getCode());
                    String signData = SignUtil.genSignData(JSON.parseObject((JSON.toJSONString(paymentRequestBean))));
                    paymentRequestBean.setSign(RSAUtil.sign(lianlianpayTax.getPrivateKey(), signData));
                    String jsonStr = JSON.toJSONString(paymentRequestBean);
                    log.info("实时付款接口请求报文：" + jsonStr);
                    // 用银通公钥对请求参数json字符串加密
                    // 报Illegal sign key
                    // size异常时，可参考这个网页解决问题http://www.wxdl.cn/java/security-invalidkey-exception.html
                    String encryptStr = null;
                    try {
                        encryptStr = LianLianPaySecurity.encrypt(jsonStr, PUBLIC_KEY_ONLINE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //	System.out.print("实时付款接口加密请求报文：" + encryptStr);
                    if (StringUtils.isEmpty(encryptStr)) {
                        paymentInventory.setPaymentStatus(-1);
                        paymentInventoryDao.updateById(paymentInventory);
                        // 加密异常
                        log.error("加密失败！");
                    }
                    JSONObject json = new JSONObject();
                    json.put("oid_partner", lianlianpayTax.getOidPartner());
                    json.put("pay_load", encryptStr);
                    log.info("实时付款接口请求报文：" + json);
                    String res = HttpUtil.post(paymentapi, JSON.toJSONString(json));
                    log.info("实时付款接口返回报文：" + res);
                    Map<String, String> paymentapiResult = JsonUtils.jsonToPojo(res, new HashMap<String, String>().getClass());
                    if (!("0000".equals(paymentapiResult.get("ret_code")))) {
                        log.error(paymentapiResult.get("ret_msg") + "\t总包ID:" + panymentOrderId + "\t支付清单ID:" + paymentInventory.getId() + "\t创客姓名:" + worker.getAccountName());
                        paymentInventory.setPaymentStatus(-1);
                        paymentInventoryDao.updateById(paymentInventory);
                        continue;
                    }
                    log.info("支付成功！");
                }
            } else {
                //商户付款失败
                paymentOrder.setPaymentOrderStatus(-1);
                paymentOrderDao.updateById(paymentOrder);
                log.error("商户付款失败:" + result.toString());
            }
        } catch (IOException e) {
            log.error(e + ":" + e.getMessage());
        }
    }

    @Override
    public ReturnJson merchantPayMany(String paymentOrderId) throws CommonException {
        PaymentOrderMany paymentOrderMany = paymentOrderManyDao.selectById(paymentOrderId);
        if (paymentOrderMany == null) {
            return ReturnJson.error("您输入的订单不存在！");
        }
        if (paymentOrderMany.getPaymentOrderStatus() > 1) {
            log.error("请勿重复支付");
            return ReturnJson.error("请勿重复支付");
        }
        Lianlianpay lianlianpay = this.getOne(new QueryWrapper<Lianlianpay>().lambda().eq(Lianlianpay::getCompanyId, paymentOrderMany.getCompanyId()));
        if (lianlianpay == null) {
            log.error("连连账号不存在！");
            return ReturnJson.error("请先添加连连商户号和私钥");
        }

        TaxPackage taxPackage = taxPackageDao.selectOne(new QueryWrapper<TaxPackage>().lambda().eq(TaxPackage::getTaxId, paymentOrderMany.getTaxId()).eq(TaxPackage::getPackageStatus, 1));
        if (taxPackage == null) {
            log.error("服务商不存在！");
            return ReturnJson.error("您输入的服务商不存在，请联系客服！");
        }

//        //查询账户余额
//        Map<String, Object> map = this.selectRemainingSum(lianlianpay.getOidPartner(), lianlianpay.getPrivateKey());
//        String ret_code = map.get("ret_code") == null ? "" : String.valueOf(map.get("ret_code"));
//        BigDecimal money = null;
//        if ("0000".equals(ret_code)) {
//            money = map.get("ret_code") == null ? new BigDecimal(0) : new BigDecimal(String.valueOf(map.get("amt_balance")));
//        } else {
//            log.error(String.valueOf(map.get("ret_msg")));
//            throw new CommonException(Integer.valueOf(ret_code), map.get("ret_msg").toString());
//        }
//
//        if (!(paymentOrderMany.getRealMoney().compareTo(money) < 0)) {
//            log.error("余额不足！");
//            return ReturnJson.error("余额不足，请先充值！");
//        }

        paymentOrderMany.setPaymentOrderStatus(4);
        paymentOrderManyDao.updateById(paymentOrderMany);
        PaymentRequestBean paymentRequestBean = new PaymentRequestBean();
        paymentRequestBean.setOid_partner(lianlianpay.getOidPartner());
        paymentRequestBean.setApi_version("1.0");
        paymentRequestBean.setNo_order(paymentOrderMany.getId());
        paymentRequestBean.setDt_order(DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN));
        paymentRequestBean.setMoney_order(paymentOrderMany.getRealMoney().setScale(2, BigDecimal.ROUND_DOWN).toString());
        paymentRequestBean.setCard_no(taxPackage.getBankCode());
        paymentRequestBean.setAcct_name(taxPackage.getPayee());
        paymentRequestBean.setInfo_order("付款");
        paymentRequestBean.setFlag_card("0");
        paymentRequestBean.setMemo("付款");
        paymentRequestBean.setNotify_url(merchantManyNotifyUrl);
        paymentRequestBean.setSign_type(SignTypeEnum.RSA.getCode());
        String signData = SignUtil.genSignData(JSON.parseObject((JSON.toJSONString(paymentRequestBean))));
        paymentRequestBean.setSign(RSAUtil.sign(lianlianpay.getPrivateKey(), signData));
        String jsonStr = JSON.toJSONString(paymentRequestBean);
        log.info("实时付款接口请求报文：" + jsonStr);
        // 用银通公钥对请求参数json字符串加密
        // 报Illegal sign key
        // size异常时，可参考这个网页解决问题http://www.wxdl.cn/java/security-invalidkey-exception.html
        String encryptStr = null;
        try {
            encryptStr = LianLianPaySecurity.encrypt(jsonStr, PUBLIC_KEY_ONLINE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //	System.out.print("实时付款接口加密请求报文：" + encryptStr);
        if (StringUtils.isEmpty(encryptStr)) {
            // 加密异常
            log.error("加密失败！");
            paymentOrderMany.setPaymentOrderStatus(1);
            paymentOrderManyDao.updateById(paymentOrderMany);
            return ReturnJson.error("支付失败！");
        }
        JSONObject json = new JSONObject();
        json.put("oid_partner", lianlianpay.getOidPartner());
        json.put("pay_load", encryptStr);
        log.info("实时付款接口请求报文：" + json);
        String res = HttpUtil.post(paymentapi, JSON.toJSONString(json));
        log.info("实时付款接口返回报文：" + res);
        Map<String, String> result = JsonUtils.jsonToPojo(res, new HashMap<String, String>().getClass());
        if ("0000".equals(result.get("ret_code"))) {
            return ReturnJson.success("后台支付中，请稍后查看！");
        } else {
            log.error(result.get("ret_msg"));
            paymentOrderMany.setPaymentOrderStatus(1);
            paymentOrderManyDao.updateById(paymentOrderMany);
            throw new CommonException(Integer.valueOf(result.get("ret_code")), result.get("ret_msg"));
        }
    }

    @Override
    public void merchantManyNotifyUrl(HttpServletRequest request) {
        String requestBody = null;
        try {
            requestBody = RealnameVerifyUtil.getRequestBody(request, "UTF-8");
            log.info(requestBody);
            HashMap<String, String> result = JsonUtils.jsonToPojo(requestBody, new HashMap<String, String>().getClass());
            log.info(result.toString());
            String panymentOrderId = result.get("no_order");
            PaymentOrderMany paymentOrderMany = paymentOrderManyDao.selectById(panymentOrderId);
            if (paymentOrderMany == null) {
                log.error("支付订单为空！");
                return;
            }
            if ("SUCCESS".equals(result.get("result_pay"))) {
                paymentOrderMany.setPaymentOrderStatus(2);
                paymentOrderMany.setPaymentDate(LocalDateTime.now());
                paymentOrderMany.setPaymentMode(1);
                paymentOrderManyDao.updateById(paymentOrderMany);
            } else {
                //商户付款失败
                paymentOrderMany.setPaymentOrderStatus(-1);
                paymentOrderManyDao.updateById(paymentOrderMany);
                log.error("商户付款失败:" + result.toString());
            }
        } catch (IOException e) {
            log.error(e + ":" + e.getMessage());
        }
    }

    private Map<String, Object> selectRemainingSum(String oidPartner, String privateKey) {
        ServicePayApplyRequest applyRequest = new ServicePayApplyRequest();
        applyRequest.setOid_partner(oidPartner);
        applyRequest.setSign_type(SignTypeEnum.RSA.getCode());
        String signData = SignUtil.genSignData(JSON.parseObject((JSON.toJSONString(applyRequest))));
        applyRequest.setSign(RSAUtil.sign(privateKey, signData));
        String res = HttpUtil.post(selectRemainingSum, JSON.toJSONString(applyRequest));
        log.info("商户号余额查询返回：" + res);
        return JsonUtils.jsonToPojo(res, new HashMap<String, Object>().getClass());
    }

    private Map<String, String> querySingleOrder(Lianlianpay lianlianpay, String paymentOrderId) {
        QuerySingleOrderRequest request = new QuerySingleOrderRequest();
        request.setOid_partner(lianlianpay.getOidPartner());
        request.setOrder_no(paymentOrderId);
        request.setSign_type(SignTypeEnum.RSA.getCode());
        String signData = SignUtil.genSignData(JSON.parseObject((JSON.toJSONString(request))));
        request.setSign(RSAUtil.sign(lianlianpay.getPrivateKey(), signData));
        String res = HttpUtil.post(querySingleOrder, JSON.toJSONString(request));
        log.info("连薪单笔代发订单查询返回报文：" + res);
        return JsonUtils.jsonToPojo(res, new HashMap<String, String>().getClass());
    }
}
