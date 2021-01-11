package com.example.merchant.dto.makerend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class QueryMissionHall implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务行业类型")
    private String industryType;

    @ApiModelProperty(value = "页码")
    @Min(value = 1)
    private Integer pageNo = 1;

    @ApiModelProperty(value = "页大小")
    private Integer pageSize = 10;
}
