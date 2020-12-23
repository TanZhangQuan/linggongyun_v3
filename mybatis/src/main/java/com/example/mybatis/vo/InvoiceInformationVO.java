package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "发票信息")
public class InvoiceInformationVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "申请开票ID")
    private String invAppId;

    @ApiModelProperty(value = "开票类目")
    private String invoiceCatalogType;

    @ApiModelProperty(value = "开票申请说明")
    private String applicationDesc;

    @ApiModelProperty(value = "开票地址")
    private String applicationAddress;

    @ApiModelProperty(value = "发票")
    private String invoiceUrl;

    @ApiModelProperty(value = "税票")
    private String taxReceiptUrl;

    @ApiModelProperty(value = "快递公司")
    private String expressCompanyName;

    @ApiModelProperty(value = "快递单号")
    private String expressSheetNo;
}
