package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "修改组织结构信息")
public class UpdateCooperationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务员ID")
    private String salesManId;

    @ApiModelProperty(value = "代理商ID")
    private String agentId;
}
