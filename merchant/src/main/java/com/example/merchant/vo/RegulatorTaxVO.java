package com.example.merchant.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "监管服务商信息")
public class RegulatorTaxVO {
    @ApiModelProperty(notes = "服务商ID", value = "服务商ID")
    private String taxId;

    @ApiModelProperty(notes = "服务商名称", value = "服务商名称")
    private String taxName;

    @ApiModelProperty(notes = "总包+分包交易流水", value = "总包+分包交易流水")
    private BigDecimal totalTab;

    @ApiModelProperty(notes = "众包交易流水", value = "众包交易流水")
    private BigDecimal manyTba;

    @ApiModelProperty(notes = "监管状态", value = "监管状态")
    private Integer status;

    @ApiModelProperty(notes = "开始监管时间", value = "开始监管时间")
    private LocalDateTime startRegulatorDate;
}
