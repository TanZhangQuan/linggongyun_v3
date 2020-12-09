package com.example.merchant.vo.platform;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description 梯度价
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/2
 */
@Data
public class CompanyLadderServiceVo {
    /**
     * 商户公司ID
     */
    private String companyTaxId;

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
