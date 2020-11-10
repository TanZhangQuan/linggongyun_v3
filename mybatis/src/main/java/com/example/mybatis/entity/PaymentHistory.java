package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.example.common.enums.OrderType;
import com.example.common.enums.PaymentType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 第三方支付记录
 * </p>
 *
 * @author hzp
 * @since 2020-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_payment_history")
public class PaymentHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 支付订单号
     */
    private String paymentOrderId;

    /**
     * 订单类型（总包，分包，支付清单）
     */
    private OrderType orderType;

    /**
     * 支付方式
     */
    private PaymentType paymentType;

    /**
     * 第三方支付账户
     */
    private String oidPartner;

    /**
     * 第三方的订单ID
     */
    private String oidPaybill;

    /**
     * 支付金额
     */
    private BigDecimal moneyOrder;

    /**
     * 支付结果
     */
    private String resultPay;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;


}
