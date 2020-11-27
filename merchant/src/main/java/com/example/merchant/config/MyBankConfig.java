package com.example.merchant.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "my-bank")
public class MyBankConfig {

    private String merchantPrivateKey;

    private String md5Key;

    private String walletPublicKey;

    private String partnerId;

    private String gopTpuUrl;

    private String gopMagUrl;

    private String asyncNotifyUrl;

    private String notifyUrl;

    private String settlementAccount;

    private String channelCode;
}
