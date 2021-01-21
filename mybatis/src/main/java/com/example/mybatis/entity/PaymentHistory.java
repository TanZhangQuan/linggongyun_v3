package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.enums.OrderType;
import com.example.common.enums.PaymentMethod;
import com.example.common.enums.TradeObject;
import com.example.common.enums.TradeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>
 * 交易记录
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
     * 交易订单号
     */
    private String tradeNo;

    /**
     * 第三方订单号
     */
    private String outerTradeNo;

    /**
     * 交易类型
     */
    private OrderType orderType;

    /**
     * 交易ID
     */
    private String orderId;

    /**
     * 交易方式
     */
    private PaymentMethod paymentMethod;

    /**
     * 交易对象类型
     */
    private TradeObject tradeObject;

    /**
     * 交易对象ID
     */
    private String tradeObjectId;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 交易结果
     */
    private TradeStatus tradeStatus;

    /**
     * 交易失败原因
     */
    private String tradeFailReason;

}
