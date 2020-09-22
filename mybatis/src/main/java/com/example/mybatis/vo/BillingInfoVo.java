package com.example.mybatis.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 支付信息
 */
@Data
public class BillingInfoVo {

    private String id;

    private String companySName;

    private String platformServiceProvider;

    private Double realMoney;

    private LocalDateTime paymentDate;
}
