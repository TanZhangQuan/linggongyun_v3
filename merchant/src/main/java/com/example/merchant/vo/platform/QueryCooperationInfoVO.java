package com.example.merchant.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "合作信息")
public class QueryCooperationInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户ID")
    private String companyInfoId;

    @ApiModelProperty(value = "业务员ID")
    private String salesManId;

    @ApiModelProperty(value = "业务员名称")
    private String salesManName;

    @ApiModelProperty(value = "代理商ID")
    private String agentId;

    @ApiModelProperty(value = "代理商名称")
    private String agentName;
}
