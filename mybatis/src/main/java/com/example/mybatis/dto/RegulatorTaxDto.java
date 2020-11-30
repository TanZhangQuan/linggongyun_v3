package com.example.mybatis.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "查询服务商列表")
public class RegulatorTaxDto {

    @ApiModelProperty(notes = "服务商名称",value = "服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(notes = "服务商id",value = "服务商id")
    private String serviceProvideId;

    @ApiModelProperty(notes = "入驻时间，开始时间", value = "入驻时间，开始时间")
    private String startDate;

    @ApiModelProperty(notes = "入驻时间，结束时间", value = "入驻时间，结束时间")
    private String endDate;

    @ApiModelProperty(notes = "当前页数", value = "当前页数")
    private Integer pageNo = 1;

    @ApiModelProperty(notes = "每页的条数", value = "每页的条数")
    private Integer pageSize = 10;
}
