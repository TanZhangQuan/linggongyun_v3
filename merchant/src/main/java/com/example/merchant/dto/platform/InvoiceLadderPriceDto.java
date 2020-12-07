package com.example.merchant.dto.platform;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/7
 */
@Data
public class InvoiceLadderPriceDto {

    /**
     * 开始的金额
     */
    private BigDecimal startMoney;

    /**
     * 结束的金额
     */
    private BigDecimal endMoney;

    /**
     * 0分包汇总代开，1分包单人单开，2众包单人单开
     */
    private Integer packaegStatus;

    /**
     * 0月度，1季度
     */
    private Integer status;

    /**
     * 服务费（如7.5，不需把百分数换算成小数）
     */
    private BigDecimal rate;
}
