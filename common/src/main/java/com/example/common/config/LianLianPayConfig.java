package com.example.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @author tzq
 * @description 连连支付配置
 * @date 2021-02-06
 */
@Component
@Validated
@ConfigurationProperties(prefix = "spring.lianlianpay")
public class LianLianPayConfig {

    /**
     * 公钥（不需替换，这是连连公钥，用于报文加密和连连返回响应报文时验签，不是商户生成的公钥）
     */
    @NotBlank
    private static String publicKey;

    /**
     * 总包付款异步回调URL
     */
    @NotBlank
    private static String totalPaymentAsyncNotifyUrl;

    /**
     * 众包付款异步回调URL
     */
    @NotBlank
    private static String manyPaymentAsyncNotifyUrl;

    /**
     * 付款创客异步回调通知URL
     */
    @NotBlank
    private static String paymentWorkerAsyncNotifyUrl;

    /**
     * 实时付款URL
     */
    @NotBlank
    private static String paymentUrl;

    /**
     * 付款确认URL
     */
    @NotBlank
    private static String confirmPaymentUrl;

    /**
     * 查询商户余额URL
     */
    @NotBlank
    private static String balanceUrl;

    /**
     * 付款结果查询URL
     */
    @NotBlank
    private static String paymentResultUrl;


    public void setPublicKey(String publicKey) {
        LianLianPayConfig.publicKey = publicKey;
    }

    public void setTotalPaymentAsyncNotifyUrl(String totalPaymentAsyncNotifyUrl) {
        LianLianPayConfig.totalPaymentAsyncNotifyUrl = totalPaymentAsyncNotifyUrl;
    }

    public void setManyPaymentAsyncNotifyUrl(String manyPaymentAsyncNotifyUrl) {
        LianLianPayConfig.manyPaymentAsyncNotifyUrl = manyPaymentAsyncNotifyUrl;
    }

    public void setPaymentWorkerAsyncNotifyUrl(String paymentWorkerAsyncNotifyUrl) {
        LianLianPayConfig.paymentWorkerAsyncNotifyUrl = paymentWorkerAsyncNotifyUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        LianLianPayConfig.paymentUrl = paymentUrl;
    }

    public void setConfirmPaymentUrl(String confirmPaymentUrl) {
        LianLianPayConfig.confirmPaymentUrl = confirmPaymentUrl;
    }

    public void setBalanceUrl(String balanceUrl) {
        LianLianPayConfig.balanceUrl = balanceUrl;
    }

    public void setPaymentResultUrl(String paymentResultUrl) {
        LianLianPayConfig.paymentResultUrl = paymentResultUrl;
    }


    public static String getPublicKey() {
        return publicKey;
    }

    public static String getTotalPaymentAsyncNotifyUrl() {
        return totalPaymentAsyncNotifyUrl;
    }

    public static String getManyPaymentAsyncNotifyUrl() {
        return manyPaymentAsyncNotifyUrl;
    }

    public static String getPaymentWorkerAsyncNotifyUrl() {
        return paymentWorkerAsyncNotifyUrl;
    }

    public static String getPaymentUrl() {
        return paymentUrl;
    }

    public static String getConfirmPaymentUrl() {
        return confirmPaymentUrl;
    }

    public static String getBalanceUrl() {
        return balanceUrl;
    }

    public static String getPaymentResultUrl() {
        return paymentResultUrl;
    }

}
