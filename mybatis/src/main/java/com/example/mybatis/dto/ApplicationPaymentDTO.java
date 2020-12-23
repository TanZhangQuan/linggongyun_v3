package com.example.mybatis.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class ApplicationPaymentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "申请开票ID")
    private String invoiceApplicationId;

    @ApiModelProperty(value = "总包支付id,多个支付id以逗号隔开")
    private String paymentOrderId;
}
