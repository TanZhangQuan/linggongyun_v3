package com.example.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @author tzq
 * @description 微信配置
 * @date 2021-02-06
 */
@Component
@Validated
@ConfigurationProperties(prefix = "spring.wechat")
public class WeChatConfig {

    /**
     * APPID
     */
    @NotBlank
    private static String appId;

    /**
     * 密钥
     */
    @NotBlank
    private static String secretKey;


    public void setAppId(String appId) {
        WeChatConfig.appId = appId;
    }

    public void setSecretKey(String secretKey) {
        WeChatConfig.secretKey = secretKey;
    }


    public static String getAppId() {
        return appId;
    }

    public static String getSecretKey() {
        return secretKey;
    }

}
