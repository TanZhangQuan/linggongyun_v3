package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXX")
public class UpdateCompanyLadderServiceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "梯度价ID")
    private String id;

    @ApiModelProperty(value = "开始的金额")
    private BigDecimal startMoney;

    @ApiModelProperty(value = "结束的金额")
    private BigDecimal endMoney;

    @ApiModelProperty(value = "服务费（如7.5，不需把百分数换算成小数）")
    private BigDecimal serviceCharge;
}
