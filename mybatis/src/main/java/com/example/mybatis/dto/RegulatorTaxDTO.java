package com.example.mybatis.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "查询服务商列表")
public class RegulatorTaxDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "服务商id")
    private String serviceProvideId;

    @ApiModelProperty(value = "入驻时间，开始时间")
    private String startDate;

    @ApiModelProperty(value = "入驻时间，结束时间")
    private String endDate;

    @ApiModelProperty(value = "当前页数")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "每页的条数")
    private Integer pageSize = 10;
}
