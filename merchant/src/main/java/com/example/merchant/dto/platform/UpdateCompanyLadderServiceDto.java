package com.example.merchant.dto.platform;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/10
 */
@Data
public class UpdateCompanyLadderServiceDto {

    /**
     * 梯度价ID
     */
    private String id;
    /**
     * 开始的金额
     */
    private BigDecimal startMoney;

    /**
     * 结束的金额
     */
    private BigDecimal endMoney;

    /**
     * 服务费（如7.5，不需把百分数换算成小数）
     */
    private BigDecimal serviceCharge;
}
