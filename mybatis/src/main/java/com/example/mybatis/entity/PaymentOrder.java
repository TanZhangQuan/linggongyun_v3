package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 支付单信息

 * </p>
 *
 * @author hzp
 * @since 2020-09-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_payment_order")
public class PaymentOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付订单ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 商户的公司ID
     */
    private String companyId;

    /**
     * 商户的公司简称
     */
    private String companySName;

    /**
     * 商家支付的金额
     */
    private BigDecimal realMoney;

    /**
     * 付给创客的金额
     */
    private BigDecimal workerMoney;

    /**
     * 平台服务商ID
     */
    private String taxId;

    /**
     * 平台服务商
     */
    private String platformServiceProvider;

    /**
     * 项目合同（存储位置）
     */
    private String companyContract;

    /**
     * 支付清单（存储位置）
     */
    private String paymentInventory;

    /**
     * 总包支付回单（存储位置）
     */
    private String turnkeyProjectPayment;

    /**
     * 分包支付回单（存储位置）
     */
    private String subpackagePayment;

    /**
     * 关联的任务(可以不关联)
     */
    private String taskId;

    /**
     * 支付验收单（存储位置）
     */
    private String acceptanceCertificate;

    /**
     * 0商户承担，1创客承担，2商户创客共同承担
     */
    private Integer taxStatus;

    /**
     * 综合税率(综合税率=商户承担的税率+创客承担的税率)
     */
    private BigDecimal compositeTax;

    /**
     * 商户承担的税率
     */
    private BigDecimal merchantTax;

    /**
     * 创客承担的税率
     */
    private BigDecimal receviceTax;

    /**
     * 支付订单的状态
     */
    private Integer paymentOrderStatus;

    /**
     * 支付时间
     */
    private LocalDateTime paymentDate;

    /**
     * 支付订单的创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 支付订单的修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;


}
