package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "查询监管部门的查询条件")
public class RegulatorQueryDto {
    @ApiModelProperty(notes = "监管部门名称", value = "监管部门名称")
    private String regulatorName;

    @ApiModelProperty(notes = "开始时间", value = "开始时间")
    private String startDate;

    @ApiModelProperty(notes = "结束时间", value = "结束时间")
    private String endDate;

    @ApiModelProperty(notes = "当前页数", value = "当前页数", required = true)
    private Integer pageNo = 0;

    @ApiModelProperty(notes = "一页的显示的条数", value = "一页的显示的条数", required = true)
    private Integer pageSize = 10;
}
