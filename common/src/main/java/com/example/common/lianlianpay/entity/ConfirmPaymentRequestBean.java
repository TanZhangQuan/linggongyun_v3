package com.example.common.lianlianpay.entity;

import lombok.Data;

/**
 * 确认付款Bean
 */
@Data
public class ConfirmPaymentRequestBean {

    private static final long serialVersionUID = -5151860453021854789L;

    /**
     * 商户付款订单号 .
     */
    private String no_order;

    /**
     * 服务器异步通知地址 .
     */
    private String notify_url;

    /**
     * 验证码 .
     */
    private String confirm_code;

    /**
     * 连连商户号 .
     */
    private String oid_partner;

    /**
     * 用户来源 .
     */
    private String platform;

    /**
     * api当前版本号 .
     */
    private String api_version = "1.0";

    /**
     * 签名方式 .
     */
    private String sign_type;

    /**
     * 签名方 .
     */
    private String sign;
}
