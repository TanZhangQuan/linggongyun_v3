package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@ApiModel(description = "银联服务商列表")
public class UnionpayTaxListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商ID")
    private String id;

    @ApiModelProperty(value = "公司全称")
    private String taxName;

    @ApiModelProperty(value = "统一的社会信用代码")
    private String creditCode;

    @ApiModelProperty(value = "公司联系人")
    private String linkMan;

    @ApiModelProperty(value = "公司联系电话")
    private String linkMobile;

    @ApiModelProperty(value = "公司的成立时间")
    private LocalDate taxCreateDate;

}
