package com.example.mybatis.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoicePO {

    /**
     * 发票类型
     */
    private Integer packageStatus;

    /**
     * 发票总金额
     */
    private BigDecimal TotalMoney;

    /**
     * 发票数量
     */
    private Integer count;
}
