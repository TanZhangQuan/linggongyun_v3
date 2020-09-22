package com.example.merchant.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel(description = "接收前端查询服务商参数")
public class TaxListDto {
    @ApiModelProperty(notes = "服务商名称", value = "服务商名称")
    private String taxName;

    @ApiModelProperty(notes = "开始时间", value = "开始时间")
    private String startDate;

    @ApiModelProperty(notes = "结束时间", value = "结束时间")
    private String endDate;

    @ApiModelProperty(notes = "当前页数", value = "当前页数",required = true)
    @Min(value = 1,message = "必须是大于0的整数")
    private Integer page;

    @ApiModelProperty(notes = "一页的条数", value = "一页的条数",required = true)
    @Min(value = 1, message = "必须是大于0的整数")
    private Integer pageSize;
}
