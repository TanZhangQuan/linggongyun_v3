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
    public static JSONObject MB010(String uid, String acctName, String companyTaxUum, String inBankNo) throws Exception {
        Map<String, String> content = Maps.newHashMap();
        content.put("uid", uid); //外部会员标识
        content.put("acct_type", "01");  //账户性质（01:企业子账户, 02:功能子账户）
        content.put("acct_name", acctName);  //子账户户名
        content.put("company_tax_num", companyTaxUum);   //企业税号
        //盛京平台户必填
        if (StringUtils.isNotBlank(inBankNo)) {
            content.put("in_bank_code", COM02(inBankNo));  //来款银行编码，网商平台户无需填写，盛京平台户必填
            content.put("in_bank_no", inBankNo);    //来款银行账号，网商平台户无需填写，盛京平台户必填
        }
        content.put("desc", "子账户申请开户");  //摘要信息
        //银联请求
        return unionpay(UnionpayMethod.MB010, content);
    }

    /**
     * 卡BIN查询
     *
     * @throws Exception
     */
    public static String COM02(String bankAcctNo) throws Exception {
        Map<String, String> content = Maps.newHashMap();
        content.put("bank_acct_no", bankAcctNo); //银行账户

        //银联请求
        JSONObject jsonObject = unionpay(UnionpayMethod.COM02, content);
        if (jsonObject == null) {
            return "";
        }

        //请求是否成功
        boolean boolSuccess = jsonObject.getBooleanValue("success");
        if (!boolSuccess) {
            return "";
        }

        //业务请求是否成功
        JSONObject returnValue = jsonObject.getJSONObject("return_value");
        String rtnCode = returnValue.getString("rtn_code");
        if (!("S00000".equals(rtnCode))) {
            String errMsg = returnValue.getString("err_msg");
            log.error("卡BIN查询失败：{}", errMsg);
            return "";
        }

        return returnValue.getString("card_bin");
    }

    /**
     * 提现出款
     *
     * @throws Exception
     */
    public static JSONObject AC041(String outerTradeNo, String uid, String amount, String destAcctName, String destAcctNo) throws Exception {
        Map<String, String> content = Maps.newHashMap();
        content.put("outer_trade_no", outerTradeNo); //合作方业务平台订单号
        content.put("uid", uid);  //外部会员标识
        content.put("amount", amount);  //提现金额
        content.put("fee", "0");   //提现手续费，暂不支持，目前暂时请传入0
        content.put("dest_acct_type", "02");  //目标账户性质，01 对公，02 对私
        content.put("dest_acct_kind", "01");   //目标账户类型 01 银行卡 11 支付宝
        content.put("dest_acct_name", destAcctName);  //目标账户户名
        content.put("dest_acct_no", destAcctNo);    //目标账户账号
        content.put("tx_call_back_addr", UnionpayConstant.TXCALLBACKADDR);   //交易回调地址
        content.put("remark", "提现出款");   //摘要信息
        //银联请求
        return unionpay(UnionpayMethod.AC041, content);
    }

    /**
     * 提现结果查询
     *
     * @throws Exception
     */
    public static JSONObject AC042(String outerTradeNo) throws Exception {
        Map<String, String> content = Maps.newHashMap();
        content.put("outer_trade_no", outerTradeNo); //合作方业务平台订单号
        //银联请求
        return unionpay(UnionpayMethod.AC042, content);
    }

    /**
     * 清分交易
     *
     * @throws Exception
     */
    public static JSONObject AC051(String outerTradeNo, String destUid, String amount) throws Exception {
        Map<String, String> content = Maps.newHashMap();
        content.put("outer_trade_no", outerTradeNo); //合作方业务平台订单号
        content.put("remark", "清分交易");   //摘要信息
        content.put("dest_uid", destUid);  //外部会员标识
        content.put("amount", amount);  //提现金额
        content.put("fee", "0");   //提现手续费，暂不支持，目前暂时请传入0
        //银联请求
        return unionpay(UnionpayMethod.AC051, content);
    }

    /**
     * 会员间交易
     *
     * @throws Exception
     */
    public static JSONObject AC054(String outerTradeNo, String uid, String destUid, String amount) throws Exception {
        Map<String, String> content = Maps.newHashMap();
        content.put("outer_trade_no", outerTradeNo); //合作方业务平台订单号
        content.put("remark", "会员间交易");  //摘要信息
        content.put("uid", uid);  //转出会员标识
        content.put("dest_uid", destUid);   //转入会员标识
        content.put("amount", amount);  //交易金额
        content.put("fee", "0");   //交易手续费，暂不支持，目前暂时请传入0
        //银联请求
        return unionpay(UnionpayMethod.AC054, content);
    }

    /**
     * 内部转账交易结果查询
     *
     * @throws Exception
     */
    public static JSONObject AC058(String outerTradeNo) throws Exception {
        Map<String, String> content = Maps.newHashMap();
        content.put("outer_trade_no", outerTradeNo); //合作方业务平台订单号
        //银联请求
        return unionpay(UnionpayMethod.AC058, content);
    }

    /**
     * 子账户账户余额查询
     *
     * @throws Exception
     */
    public static JSONObject AC081(String uid) throws Exception {
        Map<String, String> content = Maps.newHashMap();
        content.put("uid", uid); //外部会员标识
        //银联请求
        return unionpay(UnionpayMethod.AC081, content);
    }

    /**
     * 平台对账文件查询
     *
     * @throws Exception
     */
    public static JSONObject AC091(Date date) throws Exception {
        Map<String, String> content = Maps.newHashMap();
        content.put("file_type", "BIL"); //文件类型 BIL:平台户电子对账单
        content.put("biz_date", new SimpleDateFormat("yyyyMMdd").format(date)); //交易日期
        //银联请求
        return unionpay(UnionpayMethod.AC091, content);
    }

    private static JSONObject unionpay(UnionpayMethod unionPayMethod, Map<String, String> content) throws Exception {
        Map<String, String> params = Maps.newHashMap();
        params.put("merch_no", UnionpayConstant.MERCHNO);//商户号
        params.put("method", unionPayMethod.getValue());
        params.put("version", "1.0");
        params.put("acct_no", UnionpayConstant.ACCTNO);//平台账户账号
        //加密业务参数
        String encrypt = AlipaySignature.rsaEncrypt(JSON.toJSONString(content), UnionpayConstant.PFMPUBKEY, "utf-8");
        params.put("content", encrypt);
        params.put("sign", createSign(params));
        log.info("请求参数：{}", JSON.toJSONString(params));

        //请求银联接口
        JSONObject jsonObject = JSON.parseObject(HttpUtil.post(UnionpayConstant.GATEWAYURL, params));
        log.info("请求结果：{}", jsonObject);

        return jsonObject;
    }

    /**
     * 签约
     *
     * @param params
     * @return
     * @throws Exception
     */
    public static String createSign(Map<String, String> params) throws Exception {
        // map类型转换
        Map<String, String> map = new HashMap<>(params.size());
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value != null) {
                map.put(key, value);
            }
        }
        map.remove("sign");
        map.remove("sign_type");

        return AlipaySignature.rsaSign(map, UnionpayConstant.PRIKEY, "utf-8");
    }

    /**
     * 验签
     *
     * @param map
     * @param publicKey
     * @param charset
     * @param signType
     * @return
     */
    public static boolean checkSign(Map<String, String> map, String publicKey, String charset, String signType) {
        try {
            return AlipaySignature.rsaCheckV1(map, publicKey, charset, signType);
        } catch (AlipayApiException e) {
            log.error(e.toString());
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        MB010("uid", "acctName", "companyTaxUum", "8888888793632850");
    }

}
