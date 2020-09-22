package com.example.mybatis.vo;

import lombok.Data;

/**
 * 支付清单
 */
@Data
public class OrderSubpackageVo {

    private String id;
    //总包支付清单
    private String paymentInventory;
    //总包支付回单
    private String turnkeyProjectPayment;
    //分包支付回单
    private String subpackagePayment;
    //支付验收单
    private String acceptanceCertificate;

}
