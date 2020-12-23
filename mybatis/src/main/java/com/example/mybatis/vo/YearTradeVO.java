package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "年交易数据")
public class YearTradeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "一月收入")
    private BigDecimal janIncome;

    @ApiModelProperty(value = "二月收入")
    private BigDecimal febIncome;

    @ApiModelProperty(value = "三月收入")
    private BigDecimal marIncome;

    @ApiModelProperty(value = "四月收入")
    private BigDecimal aprIncome;

    @ApiModelProperty(value = "五月收入")
    private BigDecimal mayIncome;

    @ApiModelProperty(value = "六月收入")
    private BigDecimal junIncome;

    @ApiModelProperty(value = "七月收入")
    private BigDecimal julIncome;

    @ApiModelProperty(value = "八月收入")
    private BigDecimal augIncome;

    @ApiModelProperty(value = "九月收入")
    private BigDecimal sepIncome;

    @ApiModelProperty(value = "十月收入")
    private BigDecimal octIncome;

    @ApiModelProperty(value = "十一月收入")
    private BigDecimal novIncome;

    @ApiModelProperty(value = "十二月收入")
    private BigDecimal decIncome;

}
