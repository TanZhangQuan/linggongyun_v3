package com.example.mybatis.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 派单dto
 */
@Data
public class WorkerTaskDto {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创客id")
    private String workerId;

    @ApiModelProperty(value = "任务id")
    private String taskId;

    @ApiModelProperty(value = "派单状态：0表示接单成功，1表示被剔除")
    private String taskStatus;

    @ApiModelProperty(value = "任务接取时间")
    private String createDate;

}
