package com.example.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @author tzq
 * @description 网商支付配置
 * @date 2021-02-06
 */
@Component
@Validated
@ConfigurationProperties(prefix = "spring.mybank")
public class MyBankConfig {

    /**
     * 网商公钥
     */
    @NotBlank
    private static String publicKey;

    /**
     * 平台私钥
     */
    @NotBlank
    private static String platformPrivateKey;

    /**
     * MD5加密密钥
     */
    @NotBlank
    private static String md5SecretKey;

    /**
     * 合作ID
     */
    @NotBlank
    private static String partnerId;

    /**
     * tpu URL
     */
    @NotBlank
    private static String gopTpuUrl;

    /**
     * mag URL
     */
    @NotBlank
    private static String gopMagUrl;

    /**
     * 同步回调URL
     */
    @NotBlank
    private static String notifyUrl;

    /**
     * 异步回调URL
     */
    @NotBlank
    private static String asyncNotifyUrl;

    /**
     * 结算账号
     */
    @NotBlank
    private static String settlementAccount;


    public void setPublicKey(String publicKey) {
        MyBankConfig.publicKey = publicKey;
    }

    public void setPlatformPrivateKey(String platformPrivateKey) {
        MyBankConfig.platformPrivateKey = platformPrivateKey;
    }

    public void setMd5SecretKey(String md5SecretKey) {
        MyBankConfig.md5SecretKey = md5SecretKey;
    }

    public void setPartnerId(String partnerId) {
        MyBankConfig.partnerId = partnerId;
    }

    public void setGopTpuUrl(String gopTpuUrl) {
        MyBankConfig.gopTpuUrl = gopTpuUrl;
    }

    public void setGopMagUrl(String gopMagUrl) {
        MyBankConfig.gopMagUrl = gopMagUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        MyBankConfig.notifyUrl = notifyUrl;
    }

    public void setAsyncNotifyUrl(String asyncNotifyUrl) {
        MyBankConfig.asyncNotifyUrl = asyncNotifyUrl;
    }

    public void setSettlementAccount(String settlementAccount) {
        MyBankConfig.settlementAccount = settlementAccount;
    }


    public static String getPublicKey() {
        return publicKey;
    }

    public static String getPlatformPrivateKey() {
        return platformPrivateKey;
    }

    public static String getMd5SecretKey() {
        return md5SecretKey;
    }

    public static String getPartnerId() {
        return partnerId;
    }

    public static String getGopTpuUrl() {
        return gopTpuUrl;
    }

    public static String getGopMagUrl() {
        return gopMagUrl;
    }

    public static String getNotifyUrl() {
        return notifyUrl;
    }

    public static String getAsyncNotifyUrl() {
        return asyncNotifyUrl;
    }

    public static String getSettlementAccount() {
        return settlementAccount;
    }

}
