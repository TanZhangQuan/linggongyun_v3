package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "查询监管部门的查询条件")
public class RegulatorQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "监管部门名称")
    private String regulatorName;

    @ApiModelProperty(value = "开始时间")
    private String startDate;

    @ApiModelProperty(value = "结束时间")
    private String endDate;

    @ApiModelProperty(value = "当前页数", required = true)
    private Integer pageNo = 0;

    @ApiModelProperty(value = "一页的显示的条数", required = true)
    private Integer pageSize = 10;
}
