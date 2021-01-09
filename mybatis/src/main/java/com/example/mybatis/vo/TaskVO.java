package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "XXXXX")
public class TaskVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务ID")
    private String taskCode;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

}
