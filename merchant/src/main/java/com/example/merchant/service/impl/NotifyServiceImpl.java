package com.example.merchant.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.common.util.UnionpayUtil;
import com.example.merchant.service.NotifyService;
import com.example.merchant.service.TaxUnionpayService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 异步回调相关接口 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Slf4j
@Service
public class NotifyServiceImpl implements NotifyService {

    @Resource
    private TaxUnionpayService taxUnionpayService;

    @Override
    public String depositNotice(HttpServletRequest request) throws Exception {

        Map<String, Object> parameters = WebUtils.getParametersStartingWith(request, "");
        log.info("沙盒模拟入金回调接收:" + JSON.toJSONString(parameters));
        //转化为json格式
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(parameters));
        //获取商户号, 查询相应的服务商银联
//        String merchNo = jsonObject.getString("merchNo");
//        TaxUnionpay taxUnionpay = taxUnionpayService.queryTaxUnionpayByMerchNo(merchNo);
//        if (taxUnionpay == null){
//            log.error("对应商户号的服务商银联不存在");
//            return "fail";
//        }

        //验签处理
        //沙盒模拟入金回调接收:{"{rcvAcctNo":"8888888793632850, amount=10000, rcvBankCode=, bizType=01,
        // sign=IKCkb nyZ0cw5WrJ6GsRgw4vx4 f1GCkSBAmVr7IVp4mV0tRS/W0BK2gYlRddkxqjoBFcx9kTLZA36o4YuCBxL572RAxPnlxnPUghZMUt7aaKc4JXvS91v1Hx/e8l IALziIAMNuO77wDUJjqwwX2SsWqiP1ySevOAkMsRuU1zw=,
        // rcvTime=20210112155845, acctNo=664676445829, notifyType=remit_sync, rcvAcctName=广州想象力有限公司, merchNo=121818722, inacctBillId=INA2021011215584571386685185,
        // signType=RSA, remarks=沙箱入金}"}
        Map<String, String> params = Maps.newHashMap();
        params.put("notify_type", jsonObject.getString("notifyType"));
        params.put("merch_no", jsonObject.getString("merchNo"));
        params.put("acct_no", jsonObject.getString("acctNo"));
        params.put("inacct_bill_id", jsonObject.getString("inacctBillId"));
        params.put("amount", jsonObject.getString("amount"));
        params.put("rcv_acct_no", jsonObject.getString("rcvAcctNo"));
        params.put("rcv_acct_name", jsonObject.getString("rcvAcctName"));
        params.put("rcv_bank_code", jsonObject.getString("rcvBankCode"));
        params.put("biz_type", jsonObject.getString("bizType"));
        params.put("rcv_time", jsonObject.getString("rcvTime"));
        params.put("remarks", jsonObject.getString("remarks"));
        params.put("sign", jsonObject.getString("sign"));
//        params.put("sign_type", jsonObject.getString("signType"));

        log.info("验签参数：{}", params);
        boolean checkPass = UnionpayUtil.checkSign(params, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCBU0fs3JDxdIdDykNiEA+SBkG2lAoZE4B1c2xSY3OlOASSwgJqfsk0zO46rRDLwMgZi44cClP1RFVp6mQiT+vZB1GgUXDXqEjvBEuW444BTurmkSNRW/Ewna7Snx1HjEiR3kA470Xq8eNik/dOPCeZfY6OpjBtgpGdLx/9934/0QIDAQAB");
        if (!checkPass) {
            log.error("验签失败");
            return "fail";
        }

        // 业务逻辑处理 ****************************


        return "success";
    }

}
