package com.example.merchant.dto.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "监管部门查询所监管的商户参数")
public class RegulatorMerchantDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户ID")
    private String companyId;

    @ApiModelProperty(value = "商户名称")
    private String companyName;

    @ApiModelProperty(value = "商户入驻时间，开始时间")
    private String startDate;

    @ApiModelProperty(value = "商户入驻时间，结束时间")
    private String endDate;

    @ApiModelProperty(value = "当前页数")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "每页的条数")
    private Integer pageSize = 10;
}
