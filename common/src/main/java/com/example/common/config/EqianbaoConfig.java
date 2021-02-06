package com.example.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author tzq
 * @description E签宝配置
 * @date 2021-02-06
 */
@Component
@Validated
@ConfigurationProperties(prefix = "spring.eqianbao")
public class EqianbaoConfig {

    /**
     * 请求URL
     */
    @NotBlank
    private static String host;

    /**
     * 应用ID
     */
    @NotBlank
    private static String applicationId;

    /**
     * 应用密钥
     */
    @NotBlank
    private static String applicationSecretKey;

    /**
     * 印章ID
     */
    private static String sealId;

    /**
     * 平台自动盖章签署区参数：页码信息
     */
    @NotBlank
    private static String platformSignPosPage;

    /**
     * 平台自动盖章签署区参数：x坐标
     */
    @NotNull
    private static Float platformSignPosX;

    /**
     * 平台自动盖章签署区参数：y坐标
     */
    @NotBlank
    private static Float platformSignPosY;

    /**
     * 签署方自动盖章签署区参数：页码信息
     */
    @NotBlank
    private static String signerAutoSignPosPage;

    /**
     * 签署方自动盖章签署区参数：x坐标
     */
    @NotBlank
    private static Float signerAutoSignPosX;

    /**
     * 签署方自动盖章签署区参数：y坐标
     */
    @NotBlank
    private static Float signerAutoSignPosY;

    /**
     * 签署方手动盖章签署区参数：页码信息
     */
    @NotBlank
    private static String signerHandSignPosPage;

    /**
     * 签署方手动盖章签署区参数：x坐标
     */
    @NotBlank
    private static Float signerHandSignPosX;

    /**
     * 签署方手动盖章签署区参数：y坐标
     */
    @NotBlank
    private static Float signerHandSignPosY;

    /**
     * 异步回调URL
     */
    @NotBlank
    private static String asyncNotifyUrl;


    public void setHost(String host) {
        EqianbaoConfig.host = host;
    }

    public void setApplicationId(String applicationId) {
        EqianbaoConfig.applicationId = applicationId;
    }

    public void setApplicationSecretKey(String applicationSecretKey) {
        EqianbaoConfig.applicationSecretKey = applicationSecretKey;
    }

    public void setSealId(String sealId) {
        EqianbaoConfig.sealId = sealId;
    }

    public void setPlatformSignPosPage(String platformSignPosPage) {
        EqianbaoConfig.platformSignPosPage = platformSignPosPage;
    }

    public void setPlatformSignPosX(Float platformSignPosX) {
        EqianbaoConfig.platformSignPosX = platformSignPosX;
    }

    public void setPlatformSignPosY(Float platformSignPosY) {
        EqianbaoConfig.platformSignPosY = platformSignPosY;
    }

    public void setSignerAutoSignPosPage(String signerAutoSignPosPage) {
        EqianbaoConfig.signerAutoSignPosPage = signerAutoSignPosPage;
    }

    public void setSignerAutoSignPosX(Float signerAutoSignPosX) {
        EqianbaoConfig.signerAutoSignPosX = signerAutoSignPosX;
    }

    public void setSignerAutoSignPosY(Float signerAutoSignPosY) {
        EqianbaoConfig.signerAutoSignPosY = signerAutoSignPosY;
    }

    public void setSignerHandSignPosPage(String signerHandSignPosPage) {
        EqianbaoConfig.signerHandSignPosPage = signerHandSignPosPage;
    }

    public void setSignerHandSignPosX(Float signerHandSignPosX) {
        EqianbaoConfig.signerHandSignPosX = signerHandSignPosX;
    }

    public void setSignerHandSignPosY(Float signerHandSignPosY) {
        EqianbaoConfig.signerHandSignPosY = signerHandSignPosY;
    }

    public void setAsyncNotifyUrl(String asyncNotifyUrl) {
        EqianbaoConfig.asyncNotifyUrl = asyncNotifyUrl;
    }


    public static String getHost() {
        return host;
    }

    public static String getApplicationId() {
        return applicationId;
    }

    public static String getApplicationSecretKey() {
        return applicationSecretKey;
    }

    public static String getSealId() {
        return sealId;
    }

    public static String getPlatformSignPosPage() {
        return platformSignPosPage;
    }

    public static Float getPlatformSignPosX() {
        return platformSignPosX;
    }

    public static Float getPlatformSignPosY() {
        return platformSignPosY;
    }

    public static String getSignerAutoSignPosPage() {
        return signerAutoSignPosPage;
    }

    public static Float getSignerAutoSignPosX() {
        return signerAutoSignPosX;
    }

    public static Float getSignerAutoSignPosY() {
        return signerAutoSignPosY;
    }

    public static String getSignerHandSignPosPage() {
        return signerHandSignPosPage;
    }

    public static Float getSignerHandSignPosX() {
        return signerHandSignPosX;
    }

    public static Float getSignerHandSignPosY() {
        return signerHandSignPosY;
    }

    public static String getAsyncNotifyUrl() {
        return asyncNotifyUrl;
    }

}
