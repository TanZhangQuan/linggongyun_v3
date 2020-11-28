package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.enums.OrderType;
import com.example.common.enums.PaymentType;
import com.example.common.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 第三方支付记录
 * </p>
 *
 * @author hzp
 * @since 2020-11-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_payment_history")
public class PaymentHistory extends BaseEntity {
    private static final long serialVersionUID = 1L;

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
     * 支付人类型
     */
    private UserType userType;

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
     * 支付时间
     */
    private LocalDateTime payDate;

}
