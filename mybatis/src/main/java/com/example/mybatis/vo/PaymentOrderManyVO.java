package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 众包开票详情页支付信息
 */
@Data
public class PaymentOrderManyVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付ID
     */
    private String id;

    /**
     * 商户名称
     */
    private String companySName;

    /**
     * 服务商名称
     */
    private String platformServiceProvider;

    /**
     * 支付清单
     */
    private String paymentInventory;

    /**
     * 交付支付验收单
     */
    private String acceptanceCertificate;

    /**
     * 支付回单
     */
    private String manyPayment;

    /**
     * 支付状态
     */
    private Integer paymentOrderStatus;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime paymentDate;

    /**
     * 价税总和
     */
    private BigDecimal realMoney;

    /**
     * 关联任务
     */
    private TaskVO taskVo;
}
