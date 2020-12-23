package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "支付清单")
public class OrderSubpackageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付清单ID")
    private String id;

    @ApiModelProperty(value = "总包支付清单")
    private String paymentInventory;

    @ApiModelProperty(value = "总包支付回单")
    private String turnkeyProjectPayment;

    @ApiModelProperty(value = "分包支付回单")
    private String subpackagePayment;

    @ApiModelProperty(value = "支付验收单")
    private String acceptanceCertificate;

}
