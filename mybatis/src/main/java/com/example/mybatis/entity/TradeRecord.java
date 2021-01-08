package com.example.mybatis.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 商户银联信息表
 * </p>
 *
 * @author hzp
 * @since 2021-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_trade_record")
public class TradeRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 支付方身份
     */
    private String payerObject;

    /**
     * 支付方ID
     */
    private String payerId;

    /**
     * 收款方身份
     */
    private String payeeObject;

    /**
     * 收款方ID
     */
    private String payeeId;

    /**
     * 支付方式
     */
    private String tradeMethod;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 订单流水号
     */
    private String outerTradeNo;

    /**
     * 交易状态
     */
    private String tradeStatus;

}
