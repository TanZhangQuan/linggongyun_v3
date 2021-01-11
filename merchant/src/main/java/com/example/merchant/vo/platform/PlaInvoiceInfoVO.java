package com.example.merchant.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXX")
public class PlaInvoiceInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发票ID")
    private String id;

    @ApiModelProperty(value = "申请开票id")
    private String applicationId;

    @ApiModelProperty(value = "开票人,销售方")
    private String invoicePrintPerson;

    @ApiModelProperty(value = "申请开票人,购买方")
    private String applicationInvoicePerson;

    @ApiModelProperty(value = "发票金额")
    private BigDecimal invoiceMoney;

    @ApiModelProperty(value = "开票类目")
    private String invoiceCatalog;

    @ApiModelProperty(value = "发票存放url")
    private String invoiceUrl;

    @ApiModelProperty(value = "税票存放url")
    private String taxReceiptUrl;

    @ApiModelProperty(value = "快递单号")
    private String expressSheetNo;

    @ApiModelProperty(value = "快递公司名称")
    private String expressCompanyName;

    @ApiModelProperty(value = "开票说明")
    private String invoiceDesc;
}
