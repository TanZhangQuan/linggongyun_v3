package com.example.mybatis.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class WorkerTaskDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创客-任务ID")
    private String id;

    @ApiModelProperty(value = "创客ID")
    private String workerId;

    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @ApiModelProperty(value = "派单状态：0表示接单成功，1表示被剔除")
    private String taskStatus;

    @ApiModelProperty(value = "任务接取时间")
    private String createDate;

}
