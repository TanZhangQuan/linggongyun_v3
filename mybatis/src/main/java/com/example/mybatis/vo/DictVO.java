package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/1/22
 */
@Data
@ApiModel(description = "字典结构")
public class DictVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典名称")
    private String dictValue;

    @ApiModelProperty(value = "字典值")
    private String dictKey;
}
