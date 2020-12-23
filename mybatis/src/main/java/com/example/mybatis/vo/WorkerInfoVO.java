package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXX")
public class WorkerInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总任务数")
    private Integer taskNumber;

    @ApiModelProperty(value = "收入总额")
    private BigDecimal totalRevenue;

    @ApiModelProperty(value = "纳税总额")
    private BigDecimal totalTax;

    @ApiModelProperty(value = "发票数")
    private Integer invoiceNumber;
}
