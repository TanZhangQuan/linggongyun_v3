package com.example.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @author tzq
 * @description 云通讯短信配置
 * @date 2021-02-06
 */
@Component
@Validated
@ConfigurationProperties(prefix = "spring.yuntongxun")
public class YuntongxunConfig {

    /**
     * 初始化服务器地址,服务器地址不需要写https://
     */
    @NotBlank
    private static String serverIp;

    /**
     * 初始化服务器端口
     */
    @NotBlank
    private static String serverPort;

    /**
     * 主账户ID
     */
    @NotBlank
    private static String accountSid;

    /**
     * 账户授权令牌
     */
    @NotBlank
    private static String authToken;

    /**
     * 应用APPID
     */
    @NotBlank
    private static String appId;

    /**
     * 短信模板ID
     */
    @NotBlank
    private static String templateId;


    public void setServerIp(String serverIp) {
        YuntongxunConfig.serverIp = serverIp;
    }

    public void setServerPort(String serverPort) {
        YuntongxunConfig.serverPort = serverPort;
    }

    public void setAccountSid(String accountSid) {
        YuntongxunConfig.accountSid = accountSid;
    }

    public void setAuthToken(String authToken) {
        YuntongxunConfig.authToken = authToken;
    }

    public void setAppId(String appId) {
        YuntongxunConfig.appId = appId;
    }

    public void setTemplateId(String templateId) {
        YuntongxunConfig.templateId = templateId;
    }


    public static String getServerIp() {
        return serverIp;
    }

    public static String getServerPort() {
        return serverPort;
    }

    public static String getAccountSid() {
        return accountSid;
    }

    public static String getAuthToken() {
        return authToken;
    }

    public static String getAppId() {
        return appId;
    }

    public static String getTemplateId() {
        return templateId;
    }

}
