package com.example.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @author tzq
 * @description 易云章配置
 * @date 2021-02-06
 */
@Component
@Validated
@ConfigurationProperties(prefix = "spring.yiyunzhang")
public class YiyunzhangConfig {

    /**
     * 请求URL
     */
    @NotBlank
    private static String host;

    /**
     * 秘钥
     */
    @NotBlank
    private static String secretKey;

    /**
     * APPID
     */
    @NotBlank
    private static String appId;

    /**
     * AES密钥
     */
    @NotBlank
    private static String aesSecretKey;

    /**
     * 平台全称（组成印章）
     */
    @NotBlank
    private static String sealPlatformName;

    /**
     * 平台法人（组成印章）
     */
    @NotBlank
    private static String sealPlatformLegalPerson;

    /**
     * 平台联系电话（组成印章）
     */
    @NotBlank
    private static String sealPlatformContactPhone;

    /**
     * 平台社会信用代码（组成印章）
     */
    @NotBlank
    private static String sealPlatformCreditCode;

    /**
     * 平台自动盖章签署区参数：页码信息
     */
    @NotBlank
    private static String platformSignPosPage;

    /**
     * 平台自动盖章签署区参数：x坐标
     */
    @NotBlank
    private static String platformSignPosX;

    /**
     * 平台自动盖章签署区参数：y坐标
     */
    @NotBlank
    private static String platformSignPosY;

    /**
     * 签署方盖章签署区参数：页码信息
     */
    @NotBlank
    private static String signerSignPosPage;

    /**
     * 签署方盖章签署区参数：x坐标
     */
    @NotBlank
    private static String signerSignPosX;

    /**
     * 签署方盖章签署区参数：y坐标
     */
    @NotBlank
    private static String signerSignPosY;

    /**
     * 异步回调URL
     */
    @NotBlank
    private static String asyncNotifyUrl;


    public void setHost(String host) {
        YiyunzhangConfig.host = host;
    }

    public void setSecretKey(String secretKey) {
        YiyunzhangConfig.secretKey = secretKey;
    }

    public void setAppId(String appId) {
        YiyunzhangConfig.appId = appId;
    }

    public void setAesSecretKey(String aesSecretKey) {
        YiyunzhangConfig.aesSecretKey = aesSecretKey;
    }

    public void setSealPlatformName(String sealPlatformName) {
        YiyunzhangConfig.sealPlatformName = sealPlatformName;
    }

    public void setSealPlatformLegalPerson(String sealPlatformLegalPerson) {
        YiyunzhangConfig.sealPlatformLegalPerson = sealPlatformLegalPerson;
    }

    public void setSealPlatformContactPhone(String sealPlatformContactPhone) {
        YiyunzhangConfig.sealPlatformContactPhone = sealPlatformContactPhone;
    }

    public void setSealPlatformCreditCode(String sealPlatformCreditCode) {
        YiyunzhangConfig.sealPlatformCreditCode = sealPlatformCreditCode;
    }

    public void setPlatformSignPosPage(String platformSignPosPage) {
        YiyunzhangConfig.platformSignPosPage = platformSignPosPage;
    }

    public void setPlatformSignPosX(String platformSignPosX) {
        YiyunzhangConfig.platformSignPosX = platformSignPosX;
    }

    public void setPlatformSignPosY(String platformSignPosY) {
        YiyunzhangConfig.platformSignPosY = platformSignPosY;
    }

    public void setSignerSignPosPage(String signerSignPosPage) {
        YiyunzhangConfig.signerSignPosPage = signerSignPosPage;
    }

    public void setSignerSignPosX(String signerSignPosX) {
        YiyunzhangConfig.signerSignPosX = signerSignPosX;
    }

    public void setSignerSignPosY(String signerSignPosY) {
        YiyunzhangConfig.signerSignPosY = signerSignPosY;
    }

    public void setAsyncNotifyUrl(String asyncNotifyUrl) {
        YiyunzhangConfig.asyncNotifyUrl = asyncNotifyUrl;
    }


    public static String getHost() {
        return host;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public static String getAppId() {
        return appId;
    }

    public static String getAesSecretKey() {
        return aesSecretKey;
    }

    public static String getSealPlatformName() {
        return sealPlatformName;
    }

    public static String getSealPlatformLegalPerson() {
        return sealPlatformLegalPerson;
    }

    public static String getSealPlatformContactPhone() {
        return sealPlatformContactPhone;
    }

    public static String getSealPlatformCreditCode() {
        return sealPlatformCreditCode;
    }

    public static String getPlatformSignPosPage() {
        return platformSignPosPage;
    }

    public static String getPlatformSignPosX() {
        return platformSignPosX;
    }

    public static String getPlatformSignPosY() {
        return platformSignPosY;
    }

    public static String getSignerSignPosPage() {
        return signerSignPosPage;
    }

    public static String getSignerSignPosX() {
        return signerSignPosX;
    }

    public static String getSignerSignPosY() {
        return signerSignPosY;
    }

    public static String getAsyncNotifyUrl() {
        return asyncNotifyUrl;
    }

}
