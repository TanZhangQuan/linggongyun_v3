package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Jwei.
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
public class YearTradeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 一月收入
     */
    private BigDecimal janIncome;

    /**
     * 二月收入
     */
    private BigDecimal febIncome;

    /**
     * 三月收入
     */
    private BigDecimal marIncome;

    /**
     * 四月收入
     */
    private BigDecimal aprIncome;

    /**
     * 五月收入
     */
    private BigDecimal mayIncome;

    /**
     * 六月收入
     */
    private BigDecimal junIncome;

    /**
     * 七月收入
     */
    private BigDecimal julIncome;

    /**
     * 八月收入
     */
    private BigDecimal augIncome;

    /**
     * 九月收入
     */
    private BigDecimal sepIncome;

    /**
     * 十月收入
     */
    private BigDecimal octIncome;

    /**
     * 十一月收入
     */
    private BigDecimal novIncome;

    /**
     * 十二月收入
     */
    private BigDecimal decIncome;

}
