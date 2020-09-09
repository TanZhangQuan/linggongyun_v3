package com.example.mybatis.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentOrderPO {

    /**
     *支付订单的类型
     */
    private Integer packageStatus;

    /**
     * 该类型的总金额
     */

    private BigDecimal totalMoney;

}
