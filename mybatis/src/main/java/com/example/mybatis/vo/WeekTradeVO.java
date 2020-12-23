package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXX")
public class WeekTradeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "周一收入")
    private BigDecimal monIncome;

    @ApiModelProperty(value = "周二收入")
    private BigDecimal tueIncome;

    @ApiModelProperty(value = "周三收入")
    private BigDecimal wedIncome;

    @ApiModelProperty(value = "周四收入")
    private BigDecimal thuIncome;

    @ApiModelProperty(value = "周五收入")
    private BigDecimal friIncome;

    @ApiModelProperty(value = "周六收入")
    private BigDecimal satIncome;

    @ApiModelProperty(value = "周七收入")
    private BigDecimal sunIncome;

}
