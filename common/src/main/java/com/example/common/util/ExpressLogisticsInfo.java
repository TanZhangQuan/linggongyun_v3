package com.example.common.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "快递的物流信息")
public class ExpressLogisticsInfo {

    @ApiModelProperty("时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String acceptTime;

    @ApiModelProperty("快递当前的物流信息")
    private String acceptStation;
}
