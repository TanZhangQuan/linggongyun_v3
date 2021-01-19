package com.example.mybatis.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "监管服务商列表")
public class TaxVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商ID")
    private String id;

    @ApiModelProperty(value = "服务商名称")
    @Excel(name = "服务商名称")
    private String taxName;

    @ApiModelProperty(value = "总包支付单数")
    @Excel(name = "总包支付单数")
    private Integer paymentOrderNum;

    @ApiModelProperty(value = "总包交易流水")
    @Excel(name = "总包交易流水")
    private BigDecimal paymentOrderMoney;

    @ApiModelProperty(value = "支付单数")
    @Excel(name = "支付单数")
    private String paymentOrderCount;

    @ApiModelProperty(value = "众包支付单数")
    @Excel(name = "众包支付单数")
    private Integer paymentOrderManyNum;

    @ApiModelProperty(value = "总包交易流水")
    @Excel(name = "总包交易流水")
    private BigDecimal paymentOrderManyMoney;

    @ApiModelProperty(value = "状态")
    @Excel(name = "状态")
    private Integer taxStatus;

    @ApiModelProperty(value = "前端使用状态")
    private String status;

    @ApiModelProperty(value = "入驻时间")
    @Excel(name = "入驻时间")
    private String createDate;
}
