package com.example.merchant.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class AddressVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "地址ID")
    private String id;

    @ApiModelProperty(value = "联系人")
    private String linkName;

    @ApiModelProperty(value = "联系电话")
    private String linkMobile;

    @ApiModelProperty(value = "详细地址")
    private String addressName;
}