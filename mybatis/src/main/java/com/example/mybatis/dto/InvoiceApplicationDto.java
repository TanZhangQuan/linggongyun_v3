package com.example.mybatis.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class InvoiceApplicationDto {

    @ApiModelProperty(value = "申请人,商户名称")
    private String applicationPerson;


    @ApiModelProperty(value = "开票总额")
    private Double invoiceTotalAmount;

    @NotNull(message = "开票类目不能为空")
    @ApiModelProperty(value = "开票类目")
    private String invoiceCatalogType;

    @ApiModelProperty(value = "申请说明")
    private String applicationDesc;

    @NotNull(message = "收件地址不能为空")
    @ApiModelProperty(value = "申请开票地址,引用地址表id")
    private String applicationAddress;

    @ApiModelProperty(value = "支付id,多个支付id以逗号隔开")
    private String paymentOrderId;
}
