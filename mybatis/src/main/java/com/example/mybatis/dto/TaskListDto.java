package com.example.mybatis.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 任务列表查询dto
 */
@Data
@ApiModel(value = "任务列表查询")
public class TaskListDto {

    //商户id
    @ApiModelProperty(notes = "商户id", value = "商户id")
    private String merchantId;

    @ApiModelProperty(notes = "商户名称，平台端使用", value = "商户名称，平台端使用")
    private String merchantName;

    //任务编号
    @ApiModelProperty(notes = "任务编号", value = "任务编号")
    private String taskCode;

    //任务名称
    @ApiModelProperty(notes = "任务名称", value = "任务名称")
    private String taskName;

    //任务创建时间
    @ApiModelProperty(notes = "发布开始时间", value = "发布开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String releaseDate;

    //任务截至时间
    @ApiModelProperty(notes = "发布截至时间", value = "发布截至时间")
    private String deadlineDate;

    //合作类型
    @ApiModelProperty(notes = "合作类型 0总包+分包,1众包", value = "合作类型 0总包+分包,1众包")
    private Integer cooperateMode;

    //页码
    @ApiModelProperty(notes = "页码数,默认第一页参数为1", value = "页码数,默认第一页参数为1")
    private Integer pageNo = 1;

    //页大小
    @ApiModelProperty(notes = "页大小,默认大小为10", value = "页大小,默认大小为10")
    private Integer pageSize = 10;
}
