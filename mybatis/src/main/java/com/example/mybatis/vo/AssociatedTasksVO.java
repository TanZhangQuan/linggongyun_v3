package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/1/5
 */
@Data
@ApiModel("可关联的任务")
public class AssociatedTasksVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "任务编号")
    private String taskCode;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "行业类型")
    private String industryType;

    @ApiModelProperty(value = "合作模式")
    private String cooperateMode;

    @ApiModelProperty(value = "任务模式")
    private String taskMode;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "创建时间")
    private String createDate;

}
