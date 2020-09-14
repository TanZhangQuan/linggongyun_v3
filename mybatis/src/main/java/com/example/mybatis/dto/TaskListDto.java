package com.example.mybatis.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 任务列表查询dto
 */
@Data
@ApiModel(value = "任务列表查询")
public class TaskListDto {

    //商户id
    @ApiModelProperty(value = "商户id")
    private String merchantId;
    //任务编号
    @ApiModelProperty(value = "任务编号")
    private String taskCode;
    //任务名称
    @ApiModelProperty(value = "任务名称")
    private String taskName;
    //任务创建时间
    @ApiModelProperty(value = "任务创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String releaseDate;
    //任务截至时间
    @ApiModelProperty(value = "任务截至时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String deadlineDate;
    //合作类型
    @ApiModelProperty(value = "合作类型 0总包+分包,1众包")
    private Integer cooperateMode;
    //页码
    @ApiModelProperty(value = "页码数,默认第一页参数为1")
    @NotNull(message = "页码数不能为空")
    private Integer pageNo;

}
