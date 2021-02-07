package com.example.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @author tzq
 * @description 快递鸟配置
 * @date 2021-02-06
 */
@Component
@Validated
@ConfigurationProperties(prefix = "spring.kuaidiniao")
public class KuaidiniaoConfig {

    /**
     * 请求url
     */
    @NotBlank
    private static String reqUrl;

    /**
     * 电商ID
     */
    @NotBlank
    private static String businessId;

    /**
     * 电商加密私钥，快递鸟提供，注意保管，不要泄漏
     */
    @NotBlank
    private static String appKey;


    public void setReqUrl(String reqUrl) {
        KuaidiniaoConfig.reqUrl = reqUrl;
    }

    public void setBusinessId(String businessId) {
        KuaidiniaoConfig.businessId = businessId;
    }

    public void setAppKey(String appKey) {
        KuaidiniaoConfig.appKey = appKey;
    }


    public static String getReqUrl() {
        return reqUrl;
    }

    public static String getBusinessId() {
        return businessId;
    }

    public static String getAppKey() {
        return appKey;
    }

}
