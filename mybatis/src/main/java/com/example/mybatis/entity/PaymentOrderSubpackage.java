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
@TableName("tb_payment_order_subpackage")
public class PaymentOrderSubpackage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付订单ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * 支付金额
     */
    private BigDecimal realMoney;

    /**
     * 平台服务商ID
     */
    private String taxId;

    /**
     * 支付清单Id
     */
    private String paymentInventoryId;

    /**
     * 分包支付回单（存储位置）
     */
    private String subpackagePayment;

    /**
     * 关联的任务(可以不关联)
     */
    private String taskId;


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
