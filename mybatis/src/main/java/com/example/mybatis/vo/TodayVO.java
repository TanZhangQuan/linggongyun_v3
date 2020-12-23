package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "今日流水")
public class TodayVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "一点钟收入")
    private BigDecimal oneHourIncome;

    @ApiModelProperty(value = "二点钟收入")
    private BigDecimal twoHourIncome;

    @ApiModelProperty(value = "三点钟收入")
    private BigDecimal threeHourIncome;

    @ApiModelProperty(value = "四点钟收入")
    private BigDecimal fourHourIncome;

    @ApiModelProperty(value = "五点钟收入")
    private BigDecimal fiveHourIncome;

    @ApiModelProperty(value = "六点钟收入")
    private BigDecimal sixHourIncome;

    @ApiModelProperty(value = "七点钟收入")
    private BigDecimal sevenHourIncome;

    @ApiModelProperty(value = "八点钟收入")
    private BigDecimal eightHourIncome;

    @ApiModelProperty(value = "九点钟收入")
    private BigDecimal nineHourIncome;

    @ApiModelProperty(value = "十点钟收入")
    private BigDecimal tenHourIncome;

    @ApiModelProperty(value = "十一点钟收入")
    private BigDecimal elevenHourIncome;

    @ApiModelProperty(value = "十二点钟收入")
    private BigDecimal twelveHourIncome;

    @ApiModelProperty(value = "十三点钟收入")
    private BigDecimal thirteenHourIncome;

    @ApiModelProperty(value = "十四点钟收入")
    private BigDecimal fourteenHourIncome;

    @ApiModelProperty(value = "十五点钟收入")
    private BigDecimal fifteenHourIncome;

    @ApiModelProperty(value = "十六点钟收入")
    private BigDecimal sixteenHourIncome;

    @ApiModelProperty(value = "十七点钟收入")
    private BigDecimal seventeenHourIncome;

    @ApiModelProperty(value = "十八点钟收入")
    private BigDecimal eighteenHourIncome;

    @ApiModelProperty(value = "十九点钟收入")
    private BigDecimal nineteenHourIncome;

    @ApiModelProperty(value = "二十点钟收入")
    private BigDecimal twentyHourIncome;

    @ApiModelProperty(value = "二十一点钟收入")
    private BigDecimal twentyOneHourIncome;

    @ApiModelProperty(value = "二十二点钟收入")
    private BigDecimal twentyTwoHourIncome;

    @ApiModelProperty(value = "二十三点钟收入")
    private BigDecimal twentyThreeHourIncome;

    @ApiModelProperty(value = "二十四点钟收入")
    private BigDecimal twentyFourHourIncome;

}
