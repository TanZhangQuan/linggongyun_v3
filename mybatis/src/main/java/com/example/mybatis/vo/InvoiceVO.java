package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class InvoiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发票ID")
    private String id;

    @ApiModelProperty(value = "申请开票ID")
    private String applicationId;

    @ApiModelProperty(value = "发票ID")
    private String invoiceCode;

    @ApiModelProperty(value = "商户名称")
    private String companySName;

    @ApiModelProperty(value = "服务商名称")
    private String platformServiceProvider;

    @ApiModelProperty(value = "开票申请说明")
    private String applicationDesc;

    @ApiModelProperty(value = "申请状态")
    private Integer applicationState;

    @ApiModelProperty(value = "申请状态")
    private String invoicePrintDate;

    @ApiModelProperty(value = "总包发票")
    private String invoiceUrl;

    @ApiModelProperty(value = "总包税票")
    private String taxReceiptUrl;

    @ApiModelProperty(value = "商户是否申请")
    private String isInvoice;

    @ApiModelProperty(value = "总包支付信息")
    private List<OrderSubpackageVO> list;
}
