package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "商户的梯度价")
public class CompanyLadderServiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务费率ID")
    private String companyLadderServiceId;

    @ApiModelProperty(value = "起始金额")
    private String startMoney;

    @ApiModelProperty(value = "结束金额")
    private String endMoney;

    @ApiModelProperty(value = "商户服务费率")
    private String serviceCharge;

    @ApiModelProperty(value = "服务商服务费率")
    private String rate;

}
