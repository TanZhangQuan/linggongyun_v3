package com.example.merchant.vo.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "商户信息统计")
public class CountRegulatorMerchantVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "监管区入驻商户总数", value = "监管区入驻商户总数")
    private Integer countMerchant;

    @ApiModelProperty(notes = "商户总包单数", value = "商户总包单数")
    private Integer totalOrderCount;

    @ApiModelProperty(notes = "商户总包的总金额", value = "商户总包的总金额")
    private BigDecimal totalMoney;

    @ApiModelProperty(notes = "总包纳税金额", value = "总纳税金额")
    private BigDecimal totalTaxMoney;

    @ApiModelProperty(notes = "商户众包单数", value = "商户众包单数")
    private Integer manyOrderCount;

    @ApiModelProperty(notes = "商户众包总金额", value = "商户众包总金额")
    private BigDecimal manyMoney;

    @ApiModelProperty(notes = "众包纳税金额", value = "众包纳税金额")
    private BigDecimal manyTaxMoney;

    public CountRegulatorMerchantVO() {
    }

    public CountRegulatorMerchantVO(Integer countMerchant, Integer totalOrderCount, BigDecimal totalMoney, BigDecimal totalTaxMoney, Integer manyOrderCount, BigDecimal manyMoney, BigDecimal manyTaxMoney) {
        this.countMerchant = countMerchant;
        this.totalOrderCount = totalOrderCount;
        this.totalMoney = totalMoney;
        this.totalTaxMoney = totalTaxMoney;
        this.manyOrderCount = manyOrderCount;
        this.manyMoney = manyMoney;
        this.manyTaxMoney = manyTaxMoney;
    }
}
