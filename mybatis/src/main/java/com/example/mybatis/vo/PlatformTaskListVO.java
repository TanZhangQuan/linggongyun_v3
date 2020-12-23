package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "平台端查询任务列表")
public class PlatformTaskListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "XXXXXX")
    private String id;

    @ApiModelProperty(value = "XXXXXX")
    private String taskCode;

    @ApiModelProperty(value = "XXXXXX")
    private String merchantName;

    @ApiModelProperty(value = "XXXXXX")
    private String taskName;

    @ApiModelProperty(value = "XXXXXX")
    private String releaseDate;

    @ApiModelProperty(value = "XXXXXX")
    private String industryType;

    @ApiModelProperty(value = "XXXXXX")
    private String releaseTime;

    @ApiModelProperty(value = "XXXXXX")
    private Integer cooperateMode;

    @ApiModelProperty(value = "XXXXXX")
    private Integer taskMode;

    @ApiModelProperty(value = "XXXXXX")
    private Integer state;

}
