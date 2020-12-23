package com.example.mybatis.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "平台任务搜索")
public class PlatformTaskDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户名称")
    private String merchantName;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "发布开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String releaseDate;

    @ApiModelProperty(value = "发布截至时间")
    private String deadlineDate;

    @ApiModelProperty(value = "合作类型 0总包+分包,1众包")
    private Integer cooperateMode;

    @ApiModelProperty(value = "页码数,默认第一页参数为1")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "页大小,默认大小为10")
    private Integer pageSize = 10;

}
