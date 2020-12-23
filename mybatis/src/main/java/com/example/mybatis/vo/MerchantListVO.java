package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class MerchantListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "XXXXXX")
    private String id;

    @ApiModelProperty(value = "XXXXXX")
    private String companyName;
}
