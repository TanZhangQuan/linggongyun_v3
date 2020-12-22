package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 支付清单
 */
@Data
public class OrderSubpackageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 总包支付清单
     */
    private String paymentInventory;

    /**
     * 总包支付回单
     */
    private String turnkeyProjectPayment;

    /**
     * 分包支付回单
     */
    private String subpackagePayment;

    /**
     * 支付验收单
     */
    private String acceptanceCertificate;

}
