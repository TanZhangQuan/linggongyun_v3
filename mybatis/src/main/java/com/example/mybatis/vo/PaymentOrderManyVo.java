package com.example.mybatis.vo;

import lombok.Data;

/**
 * 众包开票详情页支付信息
 */
@Data
public class PaymentOrderManyVo {
    private String id;
    private String companySName;
    private String platformServiceProvider;
    private String paymentInventory;
    private String acceptanceCertificate;
    private String manyPayment;
    private String paymentOrderStatus;
    private String paymentDate;
    private Double realMoney;
    private String subpackagePayment;
    private TaskVo taskVo;
}
