package com.example.merchant.dto.platform;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/7
 */
@Data
public class TaxPackageDto {
    /**
     * 税号
     */
    private String invoiceTaxno;

    /**
     * 税费率成本
     */
    private BigDecimal taxPrice;

    /**
     * 建议市场价最小值
     */
    private BigDecimal taxMinPrice;

    /**
     * 建议市场价最大值
     */
    private BigDecimal taxMaxPrice;

    /**
     * 收款方户名
     */
    private String payee;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankCode;

    /**
     * 0总包，1众包
     */
    private Integer packageStatus;

    /**
     * 支持的类目ID 逗号分隔 全量更新
     */
    private String supportCategory;
}
