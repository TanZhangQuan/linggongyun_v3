package com.example.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @author tzq
 * @description 签约配置
 * @date 2021-02-06
 */
@Component
@Validated
@ConfigurationProperties(prefix = "spring.sign")
public class SignConfig {

    /**
     * 第三方：eqianbao E签宝，yiyunzhang 易云章
     */
    @NotBlank
    private static String partyType;


    public void setPartyType(String partyType) {
        SignConfig.partyType = partyType;
    }


    public static String getPartyType() {
        return partyType;
    }

}
