package com.example.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.example.common.constant.UnionpayConstant;
import com.example.common.enums.UnionpayMethod;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 银联请求参数加密
 *
 * @author tzq
 * @since 2020/6/5 19:08
 */
@Slf4j
public class UnionpayUtil {

    /**
     * 子账户申请开户
     *
     * @throws Exception
     */
    public static JSONObject MB010(String merchNo, String acctNo, String pfmpubkey, String prikey, String uid, String acctName, String companyTaxUum, String inBankNo) throws Exception {
        Map<String, Object> content = Maps.newHashMap();
        content.put("uid", uid); //外部会员标识
        content.put("acct_type", "01");  //账户性质（01:企业子账户, 02:功能子账户）
        content.put("acct_name", acctName);  //子账户户名
        content.put("company_tax_num", companyTaxUum);   //企业税号
        //盛京平台户必填
        if (StringUtils.isNotBlank(inBankNo)) {
            //获取银行bin
            JSONObject jsonObject = COM02(merchNo, acctNo, pfmpubkey, prikey, inBankNo);
            if (jsonObject == null) {
                log.error("请求获取银行bin失败");
                return null;
            }

            Boolean boolSuccess = jsonObject.getBoolean("success");
            if (boolSuccess == null || !boolSuccess) {
                String errMsg = jsonObject.getString("err_msg");
                log.error("{}卡BIN查询失败：{}", acctNo, errMsg);
                return jsonObject;
            }

            JSONObject returnValue = jsonObject.getJSONObject("return_value");
            String rtnCode = returnValue.getString("rtn_code");
            if (!("S00000".equals(rtnCode))) {
                String errMsg = returnValue.getString("err_msg");
                log.error("{}卡BIN查询失败：{}", acctNo, errMsg);
                return returnValue;
            }

            content.put("in_bank_code", returnValue.getString("card_bin"));  //来款银行编码，网商平台户无需填写，盛京平台户必填
            content.put("in_bank_no", inBankNo);    //来款银行账号，网商平台户无需填写，盛京平台户必填
        }
        content.put("desc", "子账户申请开户");  //摘要信息
        //银联请求
        return unionpay(merchNo, UnionpayMethod.MB010, acctNo, content, pfmpubkey, prikey);
    }

    /**
     * 卡BIN查询
     *
     * @throws Exception
     */
    public static JSONObject COM02(String merchNo, String acctNo, String pfmpubkey, String prikey, String bankAcctNo) throws Exception {
        Map<String, Object> content = Maps.newHashMap();
        content.put("bank_acct_no", bankAcctNo); //银行账户
        //银联请求
        return unionpay(merchNo, UnionpayMethod.COM02, acctNo, content, pfmpubkey, prikey);
    }

    /**
     * 子账户更换绑卡
     *
     * @throws Exception
     */
    public static JSONObject AC021(String merchNo, String acctNo, String pfmpubkey, String prikey, String uid, String inBankNo) throws Exception {
        Map<String, Object> content = Maps.newHashMap();
        content.put("uid", uid);  //外部会员标识

        //获取银行bin
        JSONObject jsonObject = COM02(merchNo, acctNo, pfmpubkey, prikey, inBankNo);
        String rtnCode = jsonObject.getString("rtn_code");
        if (StringUtils.isNotBlank(rtnCode) && !("S00000".equals(rtnCode))) {
            String errMsg = jsonObject.getString("err_msg");
            log.error("{}卡BIN查询失败：{}", acctNo, errMsg);
            return jsonObject;
        }

        JSONObject returnValue = jsonObject.getJSONObject("return_value");
        rtnCode = returnValue.getString("rtn_code");
        if (!("S00000".equals(rtnCode))) {
            String errMsg = returnValue.getString("err_msg");
            log.error("{}卡BIN查询失败：{}", acctNo, errMsg);
            return returnValue;
        }

        content.put("in_bank_code", returnValue.getString("card_bin"));  //来款银行编码，网商平台户无需填写，盛京平台户必填
        content.put("in_bank_no", inBankNo);    //来款银行账号，网商平台户无需填写，盛京平台户必填
        //银联请求
        return unionpay(merchNo, UnionpayMethod.AC041, acctNo, content, pfmpubkey, prikey);
    }

    /**
     * 提现出款
     *
     * @throws Exception
     */
    public static JSONObject AC041(String merchNo, String acctNo, String pfmpubkey, String prikey, String outerTradeNo, String uid, BigDecimal amount, String destAcctName, String destAcctNo) throws Exception {
        Map<String, Object> content = Maps.newHashMap();
        content.put("outer_trade_no", outerTradeNo); //合作方业务平台订单号
        content.put("uid", uid);  //外部会员标识
        content.put("amount", String.valueOf(amount.setScale(2, RoundingMode.HALF_UP)));  //提现金额
        content.put("fee", "0");   //提现手续费，暂不支持，目前暂时请传入0
        content.put("dest_acct_type", "02");  //目标账户性质，01 对公，02 对私
        content.put("dest_acct_kind", "01");   //目标账户类型 01 银行卡 11 支付宝
        content.put("dest_acct_name", destAcctName);  //目标账户户名
        content.put("dest_acct_no", destAcctNo);    //目标账户账号
        content.put("tx_call_back_addr", UnionpayConstant.TXCALLBACKADDR);   //交易回调地址
        content.put("remark", "提现出款");   //摘要信息
        //银联请求
        return unionpay(merchNo, UnionpayMethod.AC041, acctNo, content, pfmpubkey, prikey);
    }

    /**
     * 提现结果查询
     *
     * @throws Exception
     */
    public static JSONObject AC042(String merchNo, String acctNo, String pfmpubkey, String prikey, String outerTradeNo) throws Exception {
        Map<String, Object> content = Maps.newHashMap();
        content.put("outer_trade_no", outerTradeNo); //合作方业务平台订单号
        //银联请求
        return unionpay(merchNo, UnionpayMethod.AC042, acctNo, content, pfmpubkey, prikey);
    }

    /**
     * 清分交易
     *
     * @throws Exception
     */
    public static JSONObject AC051(String merchNo, String acctNo, String pfmpubkey, String prikey, String outerTradeNo, String destUid, BigDecimal amount) throws Exception {
        Map<String, Object> content = Maps.newHashMap();
        content.put("outer_trade_no", outerTradeNo); //合作方业务平台订单号
        content.put("remark", "清分交易");   //摘要信息
        content.put("dest_uid", destUid);  //外部会员标识
        content.put("amount", String.valueOf(amount.setScale(2, RoundingMode.HALF_UP)));  //提现金额
        content.put("fee", "0");   //提现手续费，暂不支持，目前暂时请传入0
        //银联请求
        return unionpay(merchNo, UnionpayMethod.AC051, acctNo, content, pfmpubkey, prikey);
    }

    /**
     * 会员间交易
     *
     * @throws Exception
     */
    public static JSONObject AC054(String merchNo, String acctNo, String pfmpubkey, String prikey, String outerTradeNo, String uid, String destUid, BigDecimal amount) throws Exception {
        Map<String, Object> content = Maps.newHashMap();
        content.put("outer_trade_no", outerTradeNo); //合作方业务平台订单号
        content.put("remark", "会员间交易");  //摘要信息
        content.put("uid", uid);  //转出会员标识
        content.put("dest_uid", destUid);   //转入会员标识
        content.put("amount", String.valueOf(amount.setScale(2, RoundingMode.HALF_UP)));  //交易金额
        content.put("fee", "0");   //交易手续费，暂不支持，目前暂时请传入0
        //银联请求
        return unionpay(merchNo, UnionpayMethod.AC054, acctNo, content, pfmpubkey, prikey);
    }

    /**
     * 内部转账交易结果查询
     *
     * @throws Exception
     */
    public static JSONObject AC058(String merchNo, String acctNo, String pfmpubkey, String prikey, String outerTradeNo) throws Exception {
        Map<String, Object> content = Maps.newHashMap();
        content.put("outer_trade_no", outerTradeNo); //合作方业务平台订单号
        //银联请求
        return unionpay(merchNo, UnionpayMethod.AC058, acctNo, content, pfmpubkey, prikey);
    }

    /**
     * 子账户账户余额查询
     *
     * @throws Exception
     */
    public static JSONObject AC081(String merchNo, String acctNo, String pfmpubkey, String prikey, String uid) throws Exception {
        Map<String, Object> content = Maps.newHashMap();
        content.put("uid", uid); //外部会员标识
        //银联请求
        return unionpay(merchNo, UnionpayMethod.AC081, acctNo, content, pfmpubkey, prikey);
    }

    /**
     * 平台对账文件查询
     *
     * @throws Exception
     */
    public static JSONObject AC091(String merchNo, String acctNo, String pfmpubkey, String prikey, Date date) throws Exception {
        Map<String, Object> content = Maps.newHashMap();
        content.put("file_type", "BIL"); //文件类型 BIL:平台户电子对账单
        content.put("biz_date", new SimpleDateFormat("yyyyMMdd").format(date)); //交易日期
        //银联请求
        return unionpay(merchNo, UnionpayMethod.AC091, acctNo, content, pfmpubkey, prikey);
    }

    private static JSONObject unionpay(String merchNo, UnionpayMethod unionPayMethod, String acctNo, Map<String, Object> content, String pfmpubkey, String prikey) throws Exception {
        Map<String, Object> params = Maps.newHashMap();
        params.put("merch_no", merchNo);//商户号
        params.put("method", unionPayMethod.getValue());
        params.put("version", "1.0");
        params.put("acct_no", acctNo);//平台账户账号
        //加密业务参数
        String encrypt;
        JSONObject jsonObject;
        try {
             encrypt = AlipaySignature.rsaEncrypt(JSON.toJSONString(content), pfmpubkey, "utf-8");
        } catch (Exception e) {
            log.error("加密业务参数异常", e);
            jsonObject = new JSONObject();
            jsonObject.put("success", false);
            jsonObject.put("err_msg", "平台公钥不正确");
            return jsonObject;
        }

        params.put("content", encrypt);
        params.put("sign", createSign(params, prikey));
        log.info("请求参数：{}", JSON.toJSONString(params));

        //请求银联接口
        jsonObject = JSON.parseObject(HttpUtil.post(UnionpayConstant.GATEWAYURL, params));
        log.info("请求结果：{}", jsonObject);

        return jsonObject;
    }

    public static String createSign(Map<String, Object> params, String prikey) {
        // map类型转换
        Map<String, String> map = new HashMap<>(params.size());
        for (String s : params.keySet()) {
            Object o = params.get(s);
            if (o != null) {
                map.put(s, o.toString());
            }
        }
        map.remove("sign");
        map.remove("sign_type");

        try {
            return AlipaySignature.rsaSign(map, prikey, "utf-8");
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Map<String, String> trimMap(Map<String, String> trimMap) {
        trimMap.entrySet().removeIf(entry -> StringUtils.isBlank(entry.getValue()));
        return trimMap;
    }

    // 验签
    public static boolean checkSign(Map<String, String> map, String publicKey, String charset, String sign_type) {
        try {
            return AlipaySignature.rsaCheckV1(trimMap(map), publicKey, charset, sign_type);
        } catch (AlipayApiException e) {
            log.error("验签异常：");
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        String merchNo = "121818722";
        String acctNo = "0880200707000000333";
        String pfmpubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQClPrAIo8gh9HCMniYWJxxws4KK4pfi4th4Sh8/FLGNUNGZd+o9oMzbzRyXUOs5fsBiAGGBuHGuhi0visTm6m5JYt4uLiNfQP/y/aGxbtYVBCE1BS1PF9MDSsfBqUA58JyOmF/WFjjwR1EpKMbGh2Db/K2wSC+qiR6ZSWDGSs2BNQIDAQAB";
        String prikey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIFTR+zckPF0h0PKQ2IQD5IGQbaUChkTgHVzbFJjc6U4BJLCAmp+yTTM7jqtEMvAyBmLjhwKU/VEVWnqZCJP69kHUaBRcNeoSO8ES5bjjgFO6uaRI1Fb8TCdrtKfHUeMSJHeQDjvRerx42KT9048J5l9jo6mMG2CkZ0vH/33fj/RAgMBAAECgYBRzkyBGETXd87Youlc7qvqwupug9afZiasJQcwVpigun6qFu9QTkMYk0le9HrbaGcrQYvzUNUrIL6m8Q3GZqfZCo8GHd8sJFzYwxSBd47+ktoT3FIGulQXQDljg8xBfR8ENDIqyrrX6dtxYTj6CZ545uIZqh8JRWfgCpnjSZaEwQJBAMHgzdmK1NgIhpNhJpRm3lkzLf1QCPExvvMb/sda2NQ6dUSvPmG5DYn2Adjnyh1fwos6NpgvcIPXj88mE3558fsCQQCqw2rw/7ykITQsqM4ZGr+NU+NR4xKXcGQzzsc2nMF1XA891lhWL3qsFLsoE2YRY7xvJjMG1hYpmbW4iq0YIPejAkAIkHSnanGShXXkZsM8hPrHd/JNIU7z0J29wXvUtJelcFujyBX6XSFS+dIFEeAkwgkm+1BUEqxmtov8u5La4GonAkBGoKDAoOWC9QDBX+guVfPYHlQs8EAmRqQLEYEvw1H4mmTrbJYIv4Z7We+2uZ9Dnf638hK0xyNPfXW9qA3Dpw8FAkEAuqsoLuPyoOTDBQ1XjajzYFLknVEWClTnGLnV5r1retxr0PLAO8hq1A4fpgmPMKBCzMpzIGDRzjZdiVdJxkJKoA==";
        MB010(merchNo, acctNo, pfmpubkey, prikey, "66666666", "广州想象力有限公司", "66666666", "8888888793632850");
    }

}
