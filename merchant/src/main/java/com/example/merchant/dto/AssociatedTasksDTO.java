package com.example.merchant.dto;

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
@ApiModel("查看可关联的任务")
public class AssociatedTasksDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("合作模式0：总包+分包1：众包")
    private Integer cooperateMode = 0;

    @ApiModelProperty(value = "第几页")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "页大小")
    private Integer pageSize = 10;


}
