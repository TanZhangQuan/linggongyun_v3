package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "XXXXX")
public class WorkerTaskInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务编号")
    private String taskCode;

    @ApiModelProperty(value = "创客名称")
    private String accountName;

    @ApiModelProperty(value = "发包方名称")
    private String companyName;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "任务金额")
    private BigDecimal taskAmount;

    @ApiModelProperty(value = "任务状态")
    private Integer taskStatus;

    @ApiModelProperty(value = "接单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime checkDate;
}
