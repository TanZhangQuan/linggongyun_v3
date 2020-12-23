package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "创客任务")
public class WorkerTaskVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @ApiModelProperty(value = "创客-任务ID")
    private String workerTaskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "任务最小金额")
    private String taskCostMin;

    @ApiModelProperty(value = "任务最高金额")
    private String taskCostMax;

    @ApiModelProperty(value = "合作类型")
    private String cooperateMode;

    @ApiModelProperty(value = "开始时间")
    private String releaseDate;

    @ApiModelProperty(value = "截至时间")
    private String deadlineDate;

    @ApiModelProperty(value = "行业类型")
    private String industryType;

    @ApiModelProperty(value = "状态")
    private String status;
}
