package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class CompanyLadderServiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务费率ID")
    private String companyLadderServiceId;

    @ApiModelProperty(value = "起始金额")
    private String startMoney;

    @ApiModelProperty(value = "结束金额")
    private String endMoney;

    @ApiModelProperty(value = "服务费率")
    private String serviceCharge;
}
