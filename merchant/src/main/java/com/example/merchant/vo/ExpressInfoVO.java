package com.example.merchant.vo;


import com.example.common.util.ExpressLogisticsInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
@ApiModel(description = "快递具体信息")
public class ExpressInfoVO {
    @ApiModelProperty("快递公司名称")
    private String expressCompanyName;

    @ApiModelProperty("快递单号")
    private String expressCode;

    @ApiModelProperty("快递物流信息")
    private List<ExpressLogisticsInfo> expressLogisticsInfos;
}
