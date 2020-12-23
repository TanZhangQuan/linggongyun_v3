package com.example.merchant.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class AgentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "代理商ID")
    private String id;

    @ApiModelProperty(value = "代理商名称")
    private String agentName;
}
