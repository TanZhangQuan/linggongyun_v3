package com.example.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author tzq
 * @description JWT配置
 * @date 2021-02-06
 */
@Component
@Validated
@ConfigurationProperties(prefix = "spring.jwt")
public class JwtConfig {

    /**
     * 加密密钥
     */
    @NotBlank
    private static String secretKey;

    /**
     * 过期时间（单位：天）
     */
    @NotNull
    private static Long expire;

    /**
     * 请求头部
     */
    @NotBlank
    private static String header;


    public void setSecretKey(String secretKey) {
        JwtConfig.secretKey = secretKey;
    }

    public void setExpire(Long expire) {
        JwtConfig.expire = expire;
    }

    public void setHeader(String header) {
        JwtConfig.header = header;
    }


    public static String getSecretKey() {
        return secretKey;
    }

    public static Long getExpire() {
        return expire;
    }

    public static String getHeader() {
        return header;
    }

}
