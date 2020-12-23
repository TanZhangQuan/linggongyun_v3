package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class TaskInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务ID")
    private String id;

    @ApiModelProperty(value = "商户名称")
    private String companyName;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "任务上限人数")
    private String upperLimit;

    @ApiModelProperty(value = "任务起始金额")
    private String taskCostMin;

    @ApiModelProperty(value = "任务结束金额")
    private String taskCostMax;

    @ApiModelProperty(value = "合作类型: 0 总包+分包 1 众包")
    private String cooperateMode;

    @ApiModelProperty(value = "发布时间")
    private String releaseDate;

    @ApiModelProperty(value = "任务模式0派单，1抢单，2混合")
    private String taskMode;
}
