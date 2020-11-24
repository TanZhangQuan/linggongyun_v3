package com.example.mybatis.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/11/23
 */
@Data
public class WorkerInfoVo {

    /**
     * 总任务数
     */
    private Integer taskNumber;

    /**
     * 收入总额
     */
    private BigDecimal totalRevenue;

    /**
     * 纳税总额
     */
    private BigDecimal totalTax;

    /**
     * 发票数
     */
    private Integer invoiceNumber;
}
