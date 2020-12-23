package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class PayVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付ID")
    private String pId;

    @ApiModelProperty(value = "支付清单")
    private String paymentTnventory;

    @ApiModelProperty(value = "支付回单")
    private String subpackagePayment;
}
