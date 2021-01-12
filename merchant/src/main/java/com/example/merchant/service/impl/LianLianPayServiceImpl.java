package com.example.merchant.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.MessageStatus;
import com.example.common.enums.OrderType;
import com.example.common.enums.PaymentType;
import com.example.common.enums.UserType;
import com.example.common.lianlianpay.entity.ConfirmPaymentRequestBean;
import com.example.common.lianlianpay.entity.PaymentRequestBean;
import com.example.common.lianlianpay.entity.ServicePayApplyRequest;
import com.example.common.lianlianpay.enums.SignTypeEnum;
import com.example.common.lianlianpay.utils.LianLianPaySecurity;
import com.example.common.lianlianpay.utils.RSAUtil;
import com.example.common.lianlianpay.utils.SignUtil;
import com.example.common.lianlianpay.utils.TraderRSAUtil;
import com.example.common.util.*;
import com.example.merchant.dto.merchant.AddLianLianPay;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.LianLianPayService;
import com.example.merchant.service.PaymentHistoryService;
import com.example.merchant.util.RealnameVerifyUtil;
import com.example.merchant.websocket.WebsocketServer;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@Service
public class LianLianPayServiceImpl extends ServiceImpl<LianlianpayDao, Lianlianpay> implements LianLianPayService {


    @Resource
    private MerchantDao merchantDao;

    @Resource
    private TaxDao taxDao;

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

    @Resource
    private WebsocketServer websocketServer;

    @Resource
    private PaymentHistoryService paymentHistoryService;


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

    @Value("${salary.url.confirmPayment}")
    private String confirmPayment;

    @Value("${salary.url.queryPayment}")
    private String queryPayment;


    @Value("${PWD_KEY}")
    private String PWD_KEY;

    @Override
    public ReturnJson addLianlianPay(String merchantId, AddLianLianPay addLianLianPay) {
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant == null) {
            return ReturnJson.error("您输入的商户不存在！");
        }
        Lianlianpay lianlianpay = null;
        lianlianpay = this.getOne(new QueryWrapper<Lianlianpay>().lambda().eq(Lianlianpay::getCompanyId, merchant.getCompanyId()));
        if (lianlianpay == null) {
            lianlianpay = new Lianlianpay();
        }
        lianlianpay.setCompanyId(merchant.getCompanyId());
        lianlianpay.setOidPartner(addLianLianPay.getOidPartner());
        lianlianpay.setPrivateKey(addLianLianPay.getPrivateKey());
        boolean flag = this.saveOrUpdate(lianlianpay);
        if (flag) {
            return ReturnJson.success("添加成功！");
        }
        return ReturnJson.error("添加失败，请重试！");
    }

    @Override
    public ReturnJson merchantPay(String merchantId, String payPassWord, String paymentOrderId) throws CommonException {
        log.info("商户总包付款接口。。。。。。。。。。。。。");
        boolean flag = this.verifyPayPwd(merchantId, payPassWord);
        if (!flag) {
            return ReturnJson.error("支付密码有误，请重新输入！");
        }

        PaymentOrder paymentOrder = paymentOrderDao.selectById(paymentOrderId);
        if (paymentOrder == null) {
            return ReturnJson.error("您输入的订单不存在！");
        }
        Lianlianpay lianlianpay = this.getOne(new QueryWrapper<Lianlianpay>().lambda().eq(Lianlianpay::getCompanyId, paymentOrder.getCompanyId()));
        if (lianlianpay == null) {
            log.error("连连账号不存在！");
            return ReturnJson.error("请先添加连连商户号和私钥");
        }

        Tax tax=taxDao.selectById(paymentOrder.getTaxId());
        if (tax == null) {
            log.error("服务商不存在！");
            return ReturnJson.error("您输入的服务商不存在，请联系客服！");
        }
        //当支付订单ID相同时加锁,防止订单重复支付提交
        synchronized (paymentOrderId) {
            log.info(paymentOrderId + "进入同步代码块。。。。。。。。。。。。。。");
            if (paymentOrder.getPaymentOrderStatus() > 1) {
                log.error("请勿重复支付");
                return ReturnJson.error("请勿重复支付");
            }
            log.info(paymentOrderId + "执行。。。。。。。。。。。。。。。。。。。");
            paymentOrder.setPaymentOrderStatus(4);
            paymentOrder.setMerchantId(merchantId);
            paymentOrderDao.updateById(paymentOrder);
        }

        //创建连连订单号，连连订单号为6位随机数+总包支付订单ID
        String no_order = Tools.getRandomNum() + paymentOrder.getId();
        log.info("连连订单号：" + no_order);

        PaymentRequestBean paymentRequestBean = new PaymentRequestBean();
        paymentRequestBean.setOid_partner(lianlianpay.getOidPartner());
        paymentRequestBean.setApi_version("1.0");
        paymentRequestBean.setNo_order(no_order);
        paymentRequestBean.setDt_order(DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN));
        paymentRequestBean.setMoney_order(paymentOrder.getRealMoney().setScale(2, BigDecimal.ROUND_DOWN).toString());
        paymentRequestBean.setCard_no(tax.getBankCode());
        paymentRequestBean.setAcct_name(tax.getTitleOfAccount());
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
            log.error(e + ":" + e.getMessage());
            paymentOrder.setPaymentOrderStatus(1);
            paymentOrderDao.updateById(paymentOrder);
            throw new CommonException(300, e.getMessage());
        }
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
        if (StringUtils.isEmpty(res)) {
            Map<String, String> queryPaymentResult = this.queryPayment(lianlianpay.getOidPartner(), no_order, lianlianpay.getPrivateKey());
            if ("0000".equals(queryPaymentResult.get("ret_code"))) {
                if ("SUCCESS".equals(queryPaymentResult.get("result_pay").toUpperCase()) || "PROCESSING".equals(queryPaymentResult.get("result_pay").toUpperCase())) {
                    paymentOrder.setPaymentOrderStatus(4);
                    paymentOrderDao.updateById(paymentOrder);
                    return ReturnJson.success("后台支付中，请稍后查看！");
                }
            }
            paymentOrder.setPaymentOrderStatus(1);
            paymentOrderDao.updateById(paymentOrder);
            return ReturnJson.error("支付失败！");
        }
        Map<String, String> result = JsonUtils.jsonToPojo(res, new HashMap<String, String>().getClass());
        boolean signCheck = TraderRSAUtil.checksign(PUBLIC_KEY_ONLINE,
                SignUtil.genSignData(JSONObject.parseObject(res)), result.get("sign"));
        if (!signCheck) {
            // 传送数据被篡改，可抛出异常，再人为介入检查原因
            log.error("返回结果验签异常,可能数据被篡改");
            paymentOrder.setPaymentOrderStatus(1);
            paymentOrderDao.updateById(paymentOrder);
            return ReturnJson.error("支付失败！");
        }

        if ("0000".equals(result.get("ret_code"))) {
            return ReturnJson.success("后台支付中，请稍后查看！");
        } else if ("4002".equals(result.get("ret_code"))) {
            // 连连内部测试环境数据(商户测试期间需要用正式的数据测试，测试时默认单笔单日单月额度50，等测试OK，和连连技术核对过业务对接逻辑后，申请走上线流程打开额度）
            ConfirmPaymentRequestBean confirmPaymentRequestBean = new ConfirmPaymentRequestBean();
            confirmPaymentRequestBean.setNo_order(no_order);
            // 当调用付款接口返回4002，4003，4004时，会返回验证码信息
            confirmPaymentRequestBean.setConfirm_code(result.get("confirm_code"));
            // 填写商户自己的接收付款结果回调异步通知 长度
            confirmPaymentRequestBean.setNotify_url(merchantNotifyUrl);
            confirmPaymentRequestBean.setOid_partner(lianlianpay.getOidPartner());
            confirmPaymentRequestBean.setSign_type(SignTypeEnum.RSA.getCode());
            String confirmPaymentRequestBeansignData = SignUtil.genSignData(JSON.parseObject((JSON.toJSONString(confirmPaymentRequestBean))));
            confirmPaymentRequestBean.setSign(RSAUtil.sign(lianlianpay.getPrivateKey(), confirmPaymentRequestBeansignData));
            String confirmPaymentRequestBeanjsonStr = JSON.toJSONString(confirmPaymentRequestBean);
            log.info("确认付款接口请求参数：" + confirmPaymentRequestBeanjsonStr);
            // 用银通公钥对请求参数json字符串加密
            // 报Illegal key
            // size异常时，可参考这个网页解决问题http://www.wxdl.cn/java/security-invalidkey-exception.html
            String confirmPaymentRequestBeanencryptStr = null;
            try {
                confirmPaymentRequestBeanencryptStr = LianLianPaySecurity.encrypt(confirmPaymentRequestBeanjsonStr, PUBLIC_KEY_ONLINE);
            } catch (Exception e) {
                paymentOrder.setPaymentOrderStatus(1);
                paymentOrderDao.updateById(paymentOrder);
                log.error(e + ":" + e.getMessage());
            }
            if (StringUtils.isEmpty(confirmPaymentRequestBeanencryptStr)) {
                // 加密异常
                log.error("加密失败！");
                paymentOrder.setPaymentOrderStatus(1);
                paymentOrderDao.updateById(paymentOrder);
                return ReturnJson.error("支付失败！");
            }
            JSONObject confirmPaymentRequestBeanjson = new JSONObject();
            confirmPaymentRequestBeanjson.put("oid_partner", lianlianpay.getOidPartner());
            confirmPaymentRequestBeanjson.put("pay_load", confirmPaymentRequestBeanencryptStr);
            log.info("确认付款接口请求报文：" + confirmPaymentRequestBeanjson);
            String confirmPaymentRequestBeanres = HttpUtil.post(confirmPayment, JSON.toJSONString(confirmPaymentRequestBeanjson));
            Map<String, String> confirmPaymentRequest = JsonUtils.jsonToPojo(confirmPaymentRequestBeanres, new HashMap<String, String>().getClass());
            log.info("确认付款接口请求返回：" + confirmPaymentRequest.toString());
            boolean confirmPaymentsignCheck = TraderRSAUtil.checksign(PUBLIC_KEY_ONLINE,
                    SignUtil.genSignData(JSONObject.parseObject(confirmPaymentRequestBeanres)), confirmPaymentRequest.get("sign"));
            if (!confirmPaymentsignCheck) {
                // 传送数据被篡改，可抛出异常，再人为介入检查原因
                log.error("返回结果验签异常,可能数据被篡改");
                paymentOrder.setPaymentOrderStatus(1);
                paymentOrderDao.updateById(paymentOrder);
                return ReturnJson.error("支付失败！");
            }
            if ("0000".equals(confirmPaymentRequest.get("ret_code"))) {
                return ReturnJson.success("后台支付中，请稍后查看！");
            } else {
                Map<String, String> queryPaymentResult = this.queryPayment(lianlianpay.getOidPartner(), no_order, lianlianpay.getPrivateKey());
                if ("0000".equals(queryPaymentResult.get("ret_code"))) {
                    if ("SUCCESS".equals(queryPaymentResult.get("result_pay").toUpperCase()) || "PROCESSING".equals(queryPaymentResult.get("result_pay").toUpperCase())) {
                        paymentOrder.setPaymentOrderStatus(4);
                        paymentOrderDao.updateById(paymentOrder);
                        return ReturnJson.success("后台支付中，请稍后查看！");
                    }
                }
                log.error(result.get("ret_msg"));
                paymentOrder.setPaymentOrderStatus(-1);
                paymentOrderDao.updateById(paymentOrder);
                throw new CommonException(Integer.valueOf(result.get("ret_code")), result.get("ret_msg"));
            }
        } else {
            Map<String, String> queryPaymentResult = this.queryPayment(lianlianpay.getOidPartner(), no_order, lianlianpay.getPrivateKey());
            if ("0000".equals(queryPaymentResult.get("ret_code"))) {
                if ("SUCCESS".equals(queryPaymentResult.get("result_pay").toUpperCase()) || "PROCESSING".equals(queryPaymentResult.get("result_pay").toUpperCase())) {
                    paymentOrder.setPaymentOrderStatus(4);
                    paymentOrderDao.updateById(paymentOrder);
                    return ReturnJson.success("后台支付中，请稍后查看！");
                }
            }
            log.error(result.get("ret_msg"));
            paymentOrder.setPaymentOrderStatus(1);
            paymentOrderDao.updateById(paymentOrder);
            throw new CommonException(Integer.valueOf(result.get("ret_code")), result.get("ret_msg"));
        }
    }

    @Override
    public void merchantNotifyUrl(HttpServletRequest request) {
        log.info("商户总包付款回调开始。。。。。。。。。。。。。");
        String requestBody = null;
        try {
            requestBody = RealnameVerifyUtil.getRequestBody(request, "UTF-8");
            log.info(requestBody);
            HashMap<String, String> result = JsonUtils.jsonToPojo(requestBody, new HashMap<String, String>().getClass());
            log.info(result.toString());
            String panymentOrderId = result.get("no_order").substring(6);
            log.info("连连订单号：" + result.get("no_order"));

            PaymentOrder paymentOrder = paymentOrderDao.selectById(panymentOrderId);
            boolean signCheck = TraderRSAUtil.checksign(PUBLIC_KEY_ONLINE,
                    SignUtil.genSignData(JSONObject.parseObject(requestBody)), result.get("sign"));
            if (!signCheck) {
                // 传送数据被篡改，可抛出异常，再人为介入检查原因
                log.error("返回结果验签异常,可能数据被篡改。数据：" + requestBody);
                paymentOrder.setPaymentOrderStatus(1);
                paymentOrderDao.updateById(paymentOrder);
                senMsg("订单" + paymentOrder.getId() + "数据异常，请联系服务人员！", result.get("no_order"), "0", UserType.ADMIN, paymentOrder.getMerchantId(), UserType.MERCHANT);
                return;
            }
            if (paymentOrder == null) {
                log.error("支付订单为空！");
                return;
            }

            //保存交易记录

            //先查看是否有交易的记录,有的话就覆盖
            PaymentHistory paymentHistory = paymentHistoryService.getOne(new QueryWrapper<PaymentHistory>().lambda().eq(PaymentHistory::getOidPaybill, result.get("oid_paybill")));
            if (paymentHistory == null) {
                paymentHistory = new PaymentHistory();
            }
            paymentHistory.setPaymentOrderId(paymentOrder.getId());
            paymentHistory.setOrderType(OrderType.TOTALORDER);
            paymentHistory.setPaymentType(PaymentType.LIANLIAN);
            paymentHistory.setOidPartner(result.get("oid_partner"));
            paymentHistory.setOidPaybill(result.get("oid_paybill"));
            paymentHistory.setMoneyOrder(new BigDecimal(result.get("money_order")));
            paymentHistory.setResultPay(result.get("result_pay"));
            paymentHistory.setUserType(UserType.MERCHANT);
            paymentHistory.setPayDate(DateUtil.parseLocalDateTime(result.get("dt_order"),DatePattern.PURE_DATETIME_PATTERN));
            paymentHistoryService.saveOrUpdate(paymentHistory);

            if ("SUCCESS".equals(result.get("result_pay").toUpperCase())) {
                paymentOrder.setPaymentOrderStatus(2);
                paymentOrder.setPaymentDate(LocalDateTime.now());
                paymentOrder.setPaymentMode(1);
                paymentOrderDao.updateById(paymentOrder);
                senMsg("订单" + paymentOrder.getId() + "支付成功！", result.get("no_order"), "0", UserType.ADMIN, paymentOrder.getMerchantId(), UserType.MERCHANT);
                LianlianpayTax lianlianpayTax = lianlianpayTaxDao.selectOne(new QueryWrapper<LianlianpayTax>().lambda().eq(LianlianpayTax::getTaxId, paymentOrder.getTaxId()));
                List<PaymentInventory> paymentInventories = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().lambda().eq(PaymentInventory::getPaymentOrderId, panymentOrderId));
                //给创客付款
                List<CommonMessage> commonMessageList = new ArrayList<>();
                for (PaymentInventory paymentInventory : paymentInventories) {
                    synchronized (paymentInventory.getId().intern()) {
                        Worker worker = workerDao.selectById(paymentInventory.getWorkerId());
                        if (worker == null) {
                            log.error("创客不存在" + "\t总包ID:" + panymentOrderId + "\t支付清单ID:" + paymentInventory.getId() + "\t创客姓名:" + worker.getAccountName());
                            paymentInventory.setPaymentStatus(-1);
                            paymentInventoryDao.updateById(paymentInventory);
                            CommonMessage commonMessage = getCommonMessage("ID为" + paymentInventory.getWorkerId() + "，姓名为" + paymentInventory.getWorkerName() + "创客不存在，请先导人创客", "", "0", UserType.ADMIN, paymentOrder.getMerchantId(), UserType.MERCHANT);
                            commonMessageList.add(commonMessage);
                            continue;
                        }
                        if (!(worker.getAgreementSign() == 2 && worker.getAttestation() == 1)) {
                            log.error("创客未认证" + "\t总包ID:" + panymentOrderId + "\t支付清单ID:" + paymentInventory.getId() + "\t创客姓名:" + worker.getAccountName());
                            paymentInventory.setPaymentStatus(-1);
                            paymentInventoryDao.updateById(paymentInventory);
                            CommonMessage merchantCommonMessage = getCommonMessage("ID为" + paymentInventory.getWorkerId() + "，姓名为" + paymentInventory.getWorkerName() + "的创客未认证，只有完成实名认证和签署加盟合同后才可以进行发薪", "", "0", UserType.ADMIN, paymentOrder.getMerchantId(), UserType.MERCHANT);
                            CommonMessage workerCommonMessage = getCommonMessage("您现在未认证，请完成实名认证和签署加盟合同后，才可以进行发薪操作", "", "0", UserType.ADMIN, worker.getId(), UserType.WORKER);
                            commonMessageList.add(merchantCommonMessage);
                            commonMessageList.add(workerCommonMessage);
                            continue;
                        }
                        List<WorkerBank> workerBanks = workerBankDao.selectList(new QueryWrapper<WorkerBank>().lambda().eq(WorkerBank::getWorkerId, worker.getId()));
                        if (VerificationCheck.listIsNull(workerBanks)) {
                            log.error("创客未绑定银行卡" + "\t总包ID:" + panymentOrderId + "\t支付清单ID:" + paymentInventory.getId() + "\t创客姓名:" + worker.getAccountName());
                            paymentInventory.setPaymentStatus(-1);
                            paymentInventoryDao.updateById(paymentInventory);
                            CommonMessage merchantCommonMessage = getCommonMessage("ID为" + paymentInventory.getWorkerId() + "，姓名为" + paymentInventory.getWorkerName() + "的创客未绑定银行卡，只有绑定银行卡后才可以进行发薪", "", "0", UserType.ADMIN, paymentOrder.getMerchantId(), UserType.MERCHANT);
                            CommonMessage workerCommonMessage = getCommonMessage("您现在未绑定银行卡，请完成绑卡操作，才可以进行发薪操作!", "", "0", UserType.ADMIN, worker.getId(), UserType.WORKER);
                            commonMessageList.add(merchantCommonMessage);
                            commonMessageList.add(workerCommonMessage);
                            continue;
                        }
                        if (paymentInventory.getPaymentStatus() == 1) {
                            log.error("订单已支付！");
                            continue;
                        }
                        //创建连连订单号，连连订单号为6位随机数+总包支付订单ID
                        String no_order = Tools.getRandomNum() + paymentInventory.getId();
                        log.info("连连订单号：" + no_order);

                        PaymentRequestBean paymentRequestBean = new PaymentRequestBean();
                        paymentRequestBean.setOid_partner(lianlianpayTax.getOidPartner());
                        paymentRequestBean.setApi_version("1.0");
                        paymentRequestBean.setNo_order(no_order);
                        paymentRequestBean.setDt_order(DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN));
                        paymentRequestBean.setMoney_order(paymentInventory.getRealMoney().setScale(2, BigDecimal.ROUND_DOWN).toString());
                        paymentRequestBean.setCard_no(workerBanks.get(0).getBankCode());
                        paymentRequestBean.setAcct_name(workerBanks.get(0).getRealName());
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
                        if (StringUtils.isEmpty(encryptStr)) {
                            paymentInventory.setPaymentStatus(-1);
                            paymentInventoryDao.updateById(paymentInventory);
                            CommonMessage merchantCommonMessage = getCommonMessage("ID为" + paymentInventory.getWorkerId() + "，姓名" + paymentInventory.getWorkerName() + "，支付时异常，请联系服务人员！", no_order, "0", UserType.ADMIN, paymentOrder.getMerchantId(), UserType.MERCHANT);
                            CommonMessage workerCommonMessage = getCommonMessage("支付时异常，请联系服务人员!", "", "0", UserType.ADMIN, worker.getId(), UserType.WORKER);
                            commonMessageList.add(merchantCommonMessage);
                            commonMessageList.add(workerCommonMessage);
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
                            Map<String, String> queryPaymentResult = this.queryPayment(lianlianpayTax.getOidPartner(), no_order, lianlianpayTax.getPrivateKey());
                            if ("0000".equals(queryPaymentResult.get("ret_code"))) {
                                if ("SUCCESS".equals(queryPaymentResult.get("result_pay").toUpperCase()) || "PROCESSING".equals(queryPaymentResult.get("result_pay").toUpperCase())) {
                                    paymentInventory.setPaymentStatus(1);
                                    paymentInventoryDao.updateById(paymentInventory);
                                    continue;
                                }
                            }
                            log.error(paymentapiResult.get("ret_msg") + "\t总包ID:" + panymentOrderId + "\t支付清单ID:" + paymentInventory.getId() + "\t创客姓名:" + worker.getAccountName());
                            paymentInventory.setPaymentStatus(-1);
                            paymentInventoryDao.updateById(paymentInventory);
                            CommonMessage merchantCommonMessage = getCommonMessage("ID为" + paymentInventory.getWorkerId() + "，姓名" + paymentInventory.getWorkerName() + "，支付时异常，请联系服务人员！", no_order, "0", UserType.ADMIN, paymentOrder.getMerchantId(), UserType.MERCHANT);
                            CommonMessage workerCommonMessage = getCommonMessage("支付时异常，请联系服务人员!", "", "0", UserType.ADMIN, worker.getId(), UserType.WORKER);
                            commonMessageList.add(merchantCommonMessage);
                            commonMessageList.add(workerCommonMessage);
                            continue;
                        }
                        log.info("支付成功！");
                    }
                }
                //付款失败时发送消息通知
                if (!VerificationCheck.listIsNull(commonMessageList)) {
                    senMsg(commonMessageList);
                }
            } else {
                //商户付款失败
                paymentOrder.setPaymentOrderStatus(-1);
                paymentOrderDao.updateById(paymentOrder);
                log.error("商户付款失败:" + result.toString());
                senMsg("订单" + paymentOrder.getId() + "支付成功！", result.get("no_order"), "0", UserType.ADMIN, paymentOrder.getMerchantId(), UserType.MERCHANT);
            }
        } catch (IOException e) {
            log.error(e + ":" + e.getMessage());
        }
        log.info("商户总包付款回调结束。。。。。。。。。。。。。");
    }

    @Override
    public ReturnJson merchantPayMany(String merchantId, String payPassWord, String paymentOrderId) throws CommonException {
        log.info("商户众包付款接口。。。。。。。。。。。。。");
        boolean flag = this.verifyPayPwd(merchantId, payPassWord);
        if (!flag) {
            return ReturnJson.error("支付密码有误，请重新输入！");
        }

        PaymentOrderMany paymentOrderMany = paymentOrderManyDao.selectById(paymentOrderId);
        if (paymentOrderMany == null) {
            return ReturnJson.error("您输入的订单不存在！");
        }

        Lianlianpay lianlianpay = this.getOne(new QueryWrapper<Lianlianpay>().lambda().eq(Lianlianpay::getCompanyId, paymentOrderMany.getCompanyId()));
        if (lianlianpay == null) {
            log.error("连连账号不存在！");
            return ReturnJson.error("请先添加连连商户号和私钥");
        }

        Tax tax=taxDao.selectById(paymentOrderMany.getTaxId());
        if (tax == null) {
            log.error("服务商不存在！");
            return ReturnJson.error("您输入的服务商不存在，请联系客服！");
        }
        //当支付订单ID相同时加锁,防止订单重复支付提交
        synchronized (paymentOrderId) {
            log.info(paymentOrderId + "进入同步代码块。。。。。。。。。。。。。。");
            if (paymentOrderMany.getPaymentOrderStatus() > 1) {
                log.error("请勿重复支付");
                return ReturnJson.error("请勿重复支付");
            }
            log.info(paymentOrderId + "执行。。。。。。。。。。。。。。");
            paymentOrderMany.setPaymentOrderStatus(4);
            paymentOrderMany.setMerchantId(merchantId);
            paymentOrderManyDao.updateById(paymentOrderMany);
        }

        //创建连连订单号，连连订单号为6位随机数+总包支付订单ID
        String no_order = Tools.getRandomNum() + paymentOrderMany.getId();
        log.info("连连订单号：" + no_order);

        PaymentRequestBean paymentRequestBean = new PaymentRequestBean();
        paymentRequestBean.setOid_partner(lianlianpay.getOidPartner());
        paymentRequestBean.setApi_version("1.0");
        paymentRequestBean.setNo_order(no_order);
        paymentRequestBean.setDt_order(DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN));
        paymentRequestBean.setMoney_order(paymentOrderMany.getRealMoney().setScale(2, BigDecimal.ROUND_DOWN).toString());
        paymentRequestBean.setCard_no(tax.getBankCode());
        paymentRequestBean.setAcct_name(tax.getTitleOfAccount());
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
            log.error(e + ":" + e.getMessage());
        }
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
        boolean signCheck = TraderRSAUtil.checksign(PUBLIC_KEY_ONLINE,
                SignUtil.genSignData(JSONObject.parseObject(res)), result.get("sign"));
        if (!signCheck) {
            // 传送数据被篡改，可抛出异常，再人为介入检查原因
            log.error("返回结果验签异常,可能数据被篡改");
            paymentOrderMany.setPaymentOrderStatus(1);
            paymentOrderManyDao.updateById(paymentOrderMany);
            return ReturnJson.error("支付失败！");
        }
        if ("0000".equals(result.get("ret_code"))) {
            return ReturnJson.success("后台支付中，请稍后查看！");
        } else if ("4002".equals(result.get("ret_code"))) {
            // 连连内部测试环境数据(商户测试期间需要用正式的数据测试，测试时默认单笔单日单月额度50，等测试OK，和连连技术核对过业务对接逻辑后，申请走上线流程打开额度）
            ConfirmPaymentRequestBean confirmPaymentRequestBean = new ConfirmPaymentRequestBean();
            confirmPaymentRequestBean.setNo_order(no_order);
            // 当调用付款接口返回4002，4003，4004时，会返回验证码信息
            confirmPaymentRequestBean.setConfirm_code(result.get("confirm_code"));
            // 填写商户自己的接收付款结果回调异步通知 长度
            confirmPaymentRequestBean.setNotify_url(merchantManyNotifyUrl);
            confirmPaymentRequestBean.setOid_partner(lianlianpay.getOidPartner());
            confirmPaymentRequestBean.setSign_type(SignTypeEnum.RSA.getCode());
            String confirmPaymentRequestBeansignData = SignUtil.genSignData(JSON.parseObject((JSON.toJSONString(confirmPaymentRequestBean))));
            confirmPaymentRequestBean.setSign(RSAUtil.sign(lianlianpay.getPrivateKey(), confirmPaymentRequestBeansignData));
            String confirmPaymentRequestBeanjsonStr = JSON.toJSONString(confirmPaymentRequestBean);
            log.info("确认付款接口请求参数：" + confirmPaymentRequestBeanjsonStr);
            // 用银通公钥对请求参数json字符串加密
            // 报Illegal key
            // size异常时，可参考这个网页解决问题http://www.wxdl.cn/java/security-invalidkey-exception.html
            String confirmPaymentRequestBeanencryptStr = null;
            try {
                confirmPaymentRequestBeanencryptStr = LianLianPaySecurity.encrypt(confirmPaymentRequestBeanjsonStr, PUBLIC_KEY_ONLINE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (StringUtils.isEmpty(confirmPaymentRequestBeanencryptStr)) {
                // 加密异常
                log.error("加密失败！");
                paymentOrderMany.setPaymentOrderStatus(1);
                paymentOrderManyDao.updateById(paymentOrderMany);
                return ReturnJson.error("支付失败！");
            }
            JSONObject confirmPaymentRequestBeanjson = new JSONObject();
            confirmPaymentRequestBeanjson.put("oid_partner", lianlianpay.getOidPartner());
            confirmPaymentRequestBeanjson.put("pay_load", confirmPaymentRequestBeanencryptStr);
            log.info("确认付款接口请求报文：" + confirmPaymentRequestBeanjson);
            String confirmPaymentRequestBeanres = HttpUtil.post(confirmPayment, JSON.toJSONString(confirmPaymentRequestBeanjson));
            Map<String, String> confirmPaymentRequest = JsonUtils.jsonToPojo(confirmPaymentRequestBeanres, new HashMap<String, String>().getClass());
            log.info("确认付款接口请求返回：" + confirmPaymentRequest.toString());
            if ("0000".equals(confirmPaymentRequest.get("ret_code"))) {
                return ReturnJson.success("后台支付中，请稍后查看！");
            } else {
                Map<String, String> queryPaymentResult = this.queryPayment(lianlianpay.getOidPartner(), no_order, lianlianpay.getPrivateKey());
                if ("0000".equals(queryPaymentResult.get("ret_code"))) {
                    if ("SUCCESS".equals(queryPaymentResult.get("result_pay").toUpperCase()) || "PROCESSING".equals(queryPaymentResult.get("result_pay").toUpperCase())) {
                        paymentOrderMany.setPaymentOrderStatus(4);
                        paymentOrderManyDao.updateById(paymentOrderMany);
                        return ReturnJson.success("后台支付中，请稍后查看！");
                    }
                }
                log.error(result.get("ret_msg"));
                paymentOrderMany.setPaymentOrderStatus(-1);
                paymentOrderManyDao.updateById(paymentOrderMany);
                throw new CommonException(Integer.valueOf(result.get("ret_code")), result.get("ret_msg"));
            }
        } else {
            Map<String, String> queryPaymentResult = this.queryPayment(lianlianpay.getOidPartner(), no_order, lianlianpay.getPrivateKey());
            if ("0000".equals(queryPaymentResult.get("ret_code"))) {
                if ("SUCCESS".equals(queryPaymentResult.get("result_pay").toUpperCase()) || "PROCESSING".equals(queryPaymentResult.get("result_pay").toUpperCase())) {
                    paymentOrderMany.setPaymentOrderStatus(4);
                    paymentOrderManyDao.updateById(paymentOrderMany);
                    return ReturnJson.success("后台支付中，请稍后查看！");
                }
            }
            log.error(result.get("ret_msg"));
            paymentOrderMany.setPaymentOrderStatus(1);
            paymentOrderManyDao.updateById(paymentOrderMany);
            throw new CommonException(Integer.valueOf(result.get("ret_code")), result.get("ret_msg"));
        }
    }

    @Override
    public void merchantManyNotifyUrl(HttpServletRequest request) {
        log.info("商户众包付款回调开始。。。。。。。。。。。。。");
        String requestBody = null;
        try {
            requestBody = RealnameVerifyUtil.getRequestBody(request, "UTF-8");
            log.info(requestBody);
            HashMap<String, String> result = JsonUtils.jsonToPojo(requestBody, new HashMap<String, String>().getClass());
            log.info(result.toString());
            String panymentOrderId = result.get("no_order").substring(6);
            log.info("连连订单号：" + result.get("no_order"));

            PaymentOrderMany paymentOrderMany = paymentOrderManyDao.selectById(panymentOrderId);
            if (paymentOrderMany == null) {
                log.error("支付订单为空！");
                return;
            }
            boolean signCheck = TraderRSAUtil.checksign(PUBLIC_KEY_ONLINE,
                    SignUtil.genSignData(JSONObject.parseObject(requestBody)), result.get("sign"));
            if (!signCheck) {
                // 传送数据被篡改，可抛出异常，再人为介入检查原因
                log.error("返回结果验签异常,可能数据被篡改");
                paymentOrderMany.setPaymentOrderStatus(1);
                paymentOrderManyDao.updateById(paymentOrderMany);
                senMsg("订单" + paymentOrderMany.getId() + "数据异常，请联系服务人员！", result.get("no_order"), "0", UserType.ADMIN, paymentOrderMany.getMerchantId(), UserType.MERCHANT);
                return;
            }

            //保存交易记录
            //先查看是否有交易的记录,有的话就覆盖
            PaymentHistory paymentHistory = paymentHistoryService.getOne(new QueryWrapper<PaymentHistory>().lambda().eq(PaymentHistory::getOidPaybill, result.get("oid_paybill")));
            if (paymentHistory == null) {
                paymentHistory = new PaymentHistory();
            }
            paymentHistory.setPaymentOrderId(paymentOrderMany.getId());
            paymentHistory.setOrderType(OrderType.MANYORDER);
            paymentHistory.setPaymentType(PaymentType.LIANLIAN);
            paymentHistory.setOidPartner(result.get("oid_partner"));
            paymentHistory.setOidPaybill(result.get("oid_paybill"));
            paymentHistory.setMoneyOrder(new BigDecimal(result.get("money_order")));
            paymentHistory.setResultPay(result.get("result_pay"));
            paymentHistory.setUserType(UserType.MERCHANT);
            paymentHistory.setPayDate(DateUtil.parseLocalDateTime(result.get("dt_order"),DatePattern.PURE_DATETIME_PATTERN));
            paymentHistoryService.saveOrUpdate(paymentHistory);

            if ("SUCCESS".equals(result.get("result_pay"))) {
                paymentOrderMany.setPaymentOrderStatus(2);
                paymentOrderMany.setPaymentDate(LocalDateTime.now());
                paymentOrderMany.setPaymentMode(1);
                paymentOrderManyDao.updateById(paymentOrderMany);
                senMsg("订单" + paymentOrderMany.getId() + "支付成功！", result.get("no_order"), "0", UserType.ADMIN, paymentOrderMany.getMerchantId(), UserType.MERCHANT);
            } else {
                //商户付款失败
                paymentOrderMany.setPaymentOrderStatus(-1);
                paymentOrderManyDao.updateById(paymentOrderMany);
                log.error("商户付款失败:" + result.toString());
                senMsg("订单" + paymentOrderMany.getId() + "支付失败", result.get("no_order"), "0", UserType.ADMIN, paymentOrderMany.getMerchantId(), UserType.MERCHANT);
            }
        } catch (IOException e) {
            log.error(e + ":" + e.getMessage());
        }
        log.info("商户众包付款回调结束。。。。。。。。。。。。。");
    }

    @Override
    public Map<String, String> queryPaymentByorderId(String merchantId, String oidPaybill) throws CommonException {
        Merchant merchant = merchantDao.selectById(merchantId);
        Lianlianpay lianlianpay = this.getOne(new QueryWrapper<Lianlianpay>().lambda().eq(Lianlianpay::getCompanyId, merchant.getCompanyId()));
        Map<String, String> result = this.queryPayment(lianlianpay.getOidPartner(), "", oidPaybill, lianlianpay.getPrivateKey());
        if ("0000".equals(result.get("ret_code"))) {
            return result;
        }
        throw new CommonException(Integer.valueOf(result.get("ret_code")), result.get("ret_msg"));
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

    private Map<String, String> queryPayment(String oidPartner, String noOrder, String privateKey) {
        PaymentRequestBean paymentRequestBean = new PaymentRequestBean();
        paymentRequestBean.setOid_partner(oidPartner);
        paymentRequestBean.setApi_version("1.0");
        paymentRequestBean.setNo_order(noOrder);
        paymentRequestBean.setSign_type(SignTypeEnum.RSA.getCode());
        String signData = SignUtil.genSignData(JSON.parseObject((JSON.toJSONString(paymentRequestBean))));
        paymentRequestBean.setSign(RSAUtil.sign(privateKey, signData));
        String res = HttpUtil.post(queryPayment, JSON.toJSONString(paymentRequestBean));
        log.info("商户付款查询返回数据：" + res);
        return JsonUtils.jsonToPojo(res, new HashMap<String, String>().getClass());
    }

    private Map<String, String> queryPayment(String oidPartner, String noOrder, String oidPaybill, String privateKey) {
        PaymentRequestBean paymentRequestBean = new PaymentRequestBean();
        paymentRequestBean.setOid_partner(oidPartner);
        paymentRequestBean.setOid_paybill(oidPaybill);
        paymentRequestBean.setApi_version("1.0");
        paymentRequestBean.setNo_order(noOrder);
        paymentRequestBean.setSign_type(SignTypeEnum.RSA.getCode());
        String signData = SignUtil.genSignData(JSON.parseObject((JSON.toJSONString(paymentRequestBean))));
        paymentRequestBean.setSign(RSAUtil.sign(privateKey, signData));
        log.info("商户付款查询参数数据：" + JSON.toJSONString(paymentRequestBean));
        String res = HttpUtil.post(queryPayment, JSON.toJSONString(paymentRequestBean));
        log.info("商户付款查询返回数据：" + res);
        return JsonUtils.jsonToPojo(res, new HashMap<String, String>().getClass());
    }

    private boolean verifyPayPwd(String merchantId, String payPassWord) {
        Merchant merchant = merchantDao.selectById(merchantId);
        return (PWD_KEY + MD5.md5(payPassWord)).equals(merchant.getPayPwd());
    }

    private ReturnJson senMsg(String msg, String NoOrder, String sendUserId, UserType sendUserType, String receiveUserId, UserType receiveUserType) {
        List<CommonMessage> commonMessageList = new ArrayList<>();
        CommonMessage commonMessage = new CommonMessage();
        commonMessage.setMessageStatus(MessageStatus.UNREADE);
        commonMessage.setNoOrder(NoOrder);
        commonMessage.setMessage(msg);
        commonMessage.setSendUserId(sendUserId);
        commonMessage.setSendUserType(sendUserType);
        commonMessage.setReceiveUserId(receiveUserId);
        commonMessage.setReceiveUserType(receiveUserType);
        commonMessageList.add(commonMessage);
        websocketServer.onMessage(JsonUtils.objectToJson(commonMessageList));
        return ReturnJson.success("发送成功！");
    }

    private ReturnJson senMsg(List<CommonMessage> commonMessageList) {
        websocketServer.onMessage(JsonUtils.objectToJson(commonMessageList));
        return ReturnJson.success("发送成功！");
    }

    private CommonMessage getCommonMessage(String msg, String NoOrder, String sendUserId, UserType sendUserType, String receiveUserId, UserType receiveUserType) {
        CommonMessage commonMessage = new CommonMessage();
        commonMessage.setMessageStatus(MessageStatus.UNREADE);
        commonMessage.setNoOrder(NoOrder);
        commonMessage.setMessage(msg);
        commonMessage.setSendUserId(sendUserId);
        commonMessage.setSendUserType(sendUserType);
        commonMessage.setReceiveUserId(receiveUserId);
        commonMessage.setReceiveUserType(receiveUserType);
        return commonMessage;
    }

}
