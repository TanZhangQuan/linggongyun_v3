package com.example.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author tzq
 * @description 全局配置
 * @date 2021-02-06
 */
@Component
@Validated
@ConfigurationProperties(prefix = "spring.global")
public class GlobalConfig {
    /**
     * 总包，众包上传清单是否需要创客认证
     */
    @NotNull
    private static boolean isNot;

    public static void setIsNot(Boolean isNot) {
        GlobalConfig.isNot = isNot;
    }

    public static Boolean getIsNot() {
        return isNot;
    }
}
