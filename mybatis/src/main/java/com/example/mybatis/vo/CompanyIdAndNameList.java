package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/1/8
 */
@Data
@ApiModel("商户ID和名称VO")
public class CompanyIdAndNameList implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户ID")
    private String id;

    @ApiModelProperty(value = "商户名称")
    private String companyName;
}
