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
@ApiModel(description = "商户信息统计")
public class CountRegulatorMerchantVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 监管区入驻商户总数
     */
    private Integer countMerchant;

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
