package com.example.merchant.vo.regulator;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "单个商户流水统计")
public class CountSingleRegulatorMerchantVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 商户总包单数
     */
    private Integer totalOrderCount;

    /**
     * 商户总包的总金额
     */
    private BigDecimal totalMoney;

    /**
     * 总包纳税金额
     */
    private BigDecimal totalTaxMoney;

    /**
     * 商户众包单数
     */
    private Integer manyOrderCount;

    /**
     * 商户众包总金额
     */
    private BigDecimal manyMoney;

    /**
     * 众包纳税金额
     */
    private BigDecimal manyTaxMoney;

}
