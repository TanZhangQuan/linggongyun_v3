package com.example.common.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "快递的物流信息")
public class ExpressLogisticsInfo {

    @ApiModelProperty("时间")
    private String acceptTime;
    @ApiModelProperty("快递当前的物流信息")
    private String acceptStation;
}
