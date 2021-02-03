package com.example.mybatis.entity;

import lombok.Data;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/2/3
 */
@Data
public class ApplicationPayment extends BaseEntity{

    /**
     * 发票申请ID
     */
    private String invoiceApplicationId;

    /**
     * 支付ID
     */
    private String paymentOrderId;
}
