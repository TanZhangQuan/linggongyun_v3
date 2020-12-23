package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class AddressDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "地址ID")
    private String id;

    @ApiModelProperty(value = "联系人")
    private String linkName;

    @ApiModelProperty(value = "联系电话")
    private String linkMobile;

    @ApiModelProperty(value = "详细地址")
    private String addressName;

    @ApiModelProperty(value = "是否默认：0为默认，1为不默认")
    private Integer isNot;
}
