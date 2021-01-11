package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "创客任务")
public class WorkerTaskVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @ApiModelProperty(value = "创客-任务ID")
    private String workerTaskId;

    @ApiModelProperty(value = "商户名称")
    private String companyName;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "任务最小金额")
    private String taskCostMin;

    @ApiModelProperty(value = "任务最高金额")
    private String taskCostMax;

    @ApiModelProperty(value = "合作类型")
    private String cooperateMode;

    @ApiModelProperty(value = "工单模式")
    private Integer taskMode;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @ApiModelProperty(value = "任务编号")
    private String taskCode;

    @ApiModelProperty(value = "所需人数")
    private Integer upperLimit;

    @ApiModelProperty(value = "任务状态")
    private Integer state;

    @ApiModelProperty(value = "状态")
    private String status;
}
