package com.example.merchant.vo.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "单个商户流水统计")
public class CountSingleRegulatorMerchantVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "公司名称", value = "公司名称")
    private String companyName;

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

    public CountSingleRegulatorMerchantVO() {
    }

    public CountSingleRegulatorMerchantVO(String companyName, Integer totalOrderCount, BigDecimal totalMoney, BigDecimal totalTaxMoney, Integer manyOrderCount, BigDecimal manyMoney, BigDecimal manyTaxMoney) {
        this.companyName = companyName;
        this.totalOrderCount = totalOrderCount;
        this.totalMoney = totalMoney;
        this.totalTaxMoney = totalTaxMoney;
        this.manyOrderCount = manyOrderCount;
        this.manyMoney = manyMoney;
        this.manyTaxMoney = manyTaxMoney;
    }
}
