package com.example.merchant.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class TaxListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "开票类目")
    private String id;

    @ApiModelProperty(value = "开票类目")
    private String taxName;

}
