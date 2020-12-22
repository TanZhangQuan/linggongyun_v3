package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 支付信息
 */
@Data
public class BillingInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String companySName;

    private String platformServiceProvider;

    private Double realMoney;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime paymentDate;
}
