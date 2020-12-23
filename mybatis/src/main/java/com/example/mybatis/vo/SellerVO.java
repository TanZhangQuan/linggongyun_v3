package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "销售方信息")
public class SellerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "税源地名称")
    private String taxName;

    @ApiModelProperty(value = "纳税人识别号")
    private String creditCode;

    @ApiModelProperty(value = "地址")
    private String taxAddress;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "开户行")
    private String bankName;

    @ApiModelProperty(value = "银行号码")
    private String bankCode;
}
