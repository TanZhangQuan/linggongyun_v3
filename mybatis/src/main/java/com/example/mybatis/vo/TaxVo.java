package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "监管服务商列表")
public class TaxVo {

    @ApiModelProperty(notes = "服务商Id", value = "服务商Id")
    private String id;

    @ApiModelProperty(notes = "服务商名称", value = "服务商名称")
    private String taxName;

    @ApiModelProperty(notes = "总包支付单数", value = "总包支付单数")
    private Integer paymentOrderNum;

    @ApiModelProperty(notes = "总包交易流水", value = "总包交易流水")
    private BigDecimal paymentOrderMoney;

    @ApiModelProperty(notes = "支付单数", value = "支付单数")
    private String paymentOrderCount;

    @ApiModelProperty(notes = "众包支付单数", value = "众包支付单数")
    private Integer paymentOrderManyNum;

    @ApiModelProperty(notes = "总包交易流水", value = "总包交易流水")
    private BigDecimal paymentOrderManyMoney;

    @ApiModelProperty(notes = "状态", value = "状态")
    private Integer taxStatus;

    @ApiModelProperty(notes = "前端使用状态", value = "前端使用状态")
    private String status;

    @ApiModelProperty(notes = "入驻时间", value = "入驻时间")
    private String createDate;
}
