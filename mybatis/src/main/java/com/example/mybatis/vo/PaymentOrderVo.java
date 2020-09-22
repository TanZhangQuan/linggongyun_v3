package com.example.mybatis.vo;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * 支付清单
 */
@Data
public class PaymentOrderVo {

    private String id;

    private String companySName;

    private String platformServiceProvider;

    private String acceptanceCertificate;

    private String paymentOrderStatus;

    private String paymentInventory;

    private LocalDateTime paymentDate;

    private String subpackagePayment;

    private TaskVo taskVo;
}
