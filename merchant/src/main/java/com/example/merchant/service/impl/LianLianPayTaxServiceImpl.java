package com.example.merchant.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.lianlianpay.entity.PaymentRequestBean;
import com.example.common.lianlianpay.enums.SignTypeEnum;
import com.example.common.lianlianpay.utils.RSAUtil;
import com.example.common.lianlianpay.utils.SignUtil;
import com.example.common.util.JsonUtils;
import com.example.common.util.ReturnJson;
import com.example.common.util.VerificationCheck;
import com.example.merchant.dto.merchant.AddLianLianPay;
import com.example.merchant.service.LianLianPayTaxService;
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
import java.util.*;


@Slf4j
@Service
public class LianLianPayTaxServiceImpl extends ServiceImpl<LianlianpayTaxDao, LianlianpayTax> implements LianLianPayTaxService {

    @Resource
    private TaxDao taxDao;

    @Resource
    private PaymentInventoryDao paymentInventoryDao;

    @Resource
    private PaymentOrderDao paymentOrderDao;

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
     * 接收商户付款异步通知地址
     */
    @Value("${salary.merchantNotifyUrl}")
    private String merchantNotifyUrl;

    /**
     * 接收商户付款异步通知地址
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
    public ReturnJson addLianlianPay(String taxId, AddLianLianPay addLianLianPay) {
        Tax tax = taxDao.selectById(taxId);
        if (tax == null) {
            return ReturnJson.error("您输入的服务商不存在！");
        }
        LianlianpayTax lianlianpayTax = new LianlianpayTax();
        lianlianpayTax.setTaxId(tax.getId());
        lianlianpayTax.setOidPartner(addLianLianPay.getOidPartner());
        lianlianpayTax.setPrivateKey(addLianLianPay.getPrivateKey());
        boolean flag = this.save(lianlianpayTax);
        if (flag) {
            return ReturnJson.success("添加成功！");
        }
        return ReturnJson.error("添加失败，请重试！");
    }

    @Override
    public void workerNotifyUrl(HttpServletRequest request) {
        String requestBody = null;
        try {
            requestBody = RealnameVerifyUtil.getRequestBody(request, "UTF-8");
            log.info(requestBody);
            HashMap<String, String> result = JsonUtils.jsonToPojo(requestBody, new HashMap<String, String>().getClass());
            log.info(result.toString());
            String paymentInventoryId = result.get("no_order");
            PaymentInventory paymentInventory = paymentInventoryDao.selectById(paymentInventoryId);
            if (paymentInventory == null) {
                log.error("支付清单为空！");
                return;
            }
            if ("SUCCESS".equals(result.get("result_pay").toUpperCase())) {
                paymentInventory.setPaymentStatus(1);
                paymentInventoryDao.updateById(paymentInventory);
                return;
            } else {
                log.error(result.toString());
                paymentInventory.setPaymentStatus(-1);
                paymentInventoryDao.updateById(paymentInventory);
                return;
            }
        } catch (IOException e) {
            log.error(e + ":" + e.getMessage());
        }
    }

    @Override
    public ReturnJson taxPay(String paymentInventoryIds) {
        List<String> ids = Arrays.asList(paymentInventoryIds.split(","));

        /**
         * 用来存储失败的数据
         */
        List<Map> errorList = new ArrayList<>();
        List<PaymentInventory> paymentInventories = paymentInventoryDao.selectBatchIds(ids);
        if (VerificationCheck.listIsNull(paymentInventories)) {
            return ReturnJson.error("订单号不存在！");
        }
        //给创客付款
        for (PaymentInventory paymentInventory : paymentInventories) {
            PaymentOrder paymentOrder = paymentOrderDao.selectById(paymentInventory.getPaymentOrderId());
            if (paymentOrder == null) {
                log.error("总包订单为空！");
                continue;
            }
            LianlianpayTax lianlianpayTax = lianlianpayTaxDao.selectOne(new QueryWrapper<LianlianpayTax>().lambda().eq(LianlianpayTax::getTaxId, paymentOrder.getTaxId()));

            Worker worker = workerDao.selectById(paymentInventory.getWorkerId());
            if (!(worker.getAgreementSign() == 2 && worker.getAttestation() == 1)) {
                log.error("创客未认证" + "\t总包ID:" + paymentOrder.getId() + "\t支付清单ID:" + paymentInventory.getId() + "\t创客姓名:" + worker.getAccountName());
                paymentInventory.setPaymentStatus(-1);
                paymentInventoryDao.updateById(paymentInventory);
                Map map = new HashMap();
                map.put("paymentInventoryId", paymentInventory.getId());
                map.put("msg", "创客未认证");
                errorList.add(map);
                continue;
            }
            List<WorkerBank> workerBanks = workerBankDao.selectList(new QueryWrapper<WorkerBank>().lambda().eq(WorkerBank::getWorkerId, worker.getId()));
            if (VerificationCheck.listIsNull(workerBanks)) {
                log.error("创客未绑定银行卡" + "\t总包ID:" + paymentOrder.getId() + "\t支付清单ID:" + paymentInventory.getId() + "\t创客姓名:" + worker.getAccountName());
                paymentInventory.setPaymentStatus(-1);
                paymentInventoryDao.updateById(paymentInventory);
                Map map = new HashMap();
                map.put("paymentInventoryId", paymentInventory.getId());
                map.put("msg", "创客未绑定银行卡");
                errorList.add(map);
                continue;
            }
            if (paymentInventory.getPaymentStatus() == 1) {
                log.error("订单已支付！");
                Map map = new HashMap();
                map.put("paymentInventoryId", paymentInventory.getId());
                map.put("msg", "订单已支付");
                errorList.add(map);
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
                Map map = new HashMap();
                map.put("paymentInventoryId", paymentInventory.getId());
                map.put("msg", "加密失败");
                errorList.add(map);
                continue;
            }
            JSONObject json = new JSONObject();
            json.put("oid_partner", lianlianpayTax.getOidPartner());
            json.put("pay_load", encryptStr);
            log.info("实时付款接口请求报文：" + json);
            String res = HttpUtil.post(paymentapi, JSON.toJSONString(json));
            log.info("实时付款接口返回报文：" + res);
            Map<String, String> paymentapiResult = JsonUtils.jsonToPojo(res, new HashMap<String, String>().getClass());
            if (!("0000".equals(paymentapiResult.get("ret_code")))) {
                log.error(paymentapiResult.get("ret_msg") + "\t总包ID:" + paymentOrder.getId() + "\t支付清单ID:" + paymentInventory.getId() + "\t创客姓名:" + worker.getAccountName());
                paymentInventory.setPaymentStatus(-1);
                paymentInventoryDao.updateById(paymentInventory);
                Map map = new HashMap();
                map.put("paymentInventoryId", paymentInventory.getId());
                map.put("msg", paymentapiResult.get("ret_msg"));
                errorList.add(map);
                continue;
            }
            log.info("支付成功！");
        }
        if (VerificationCheck.listIsNull(errorList)) {
            return ReturnJson.success("后台支付中，请稍后查看！");
        }
        return ReturnJson.error("部分支付成功！", errorList);
    }
}
