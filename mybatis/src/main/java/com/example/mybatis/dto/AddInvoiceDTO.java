package com.example.mybatis.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "总包开票")
public class AddInvoiceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总包ID")
    private String id;

    @ApiModelProperty(value = "申请开票ID")
    @NotNull(message = "申请开票ID不能为空")
    private String applicationId;

    @ApiModelProperty(value = "开票人,销售方")
    @NotNull(message = "开票人不能为空")
    private String invoicePrintPerson;

    @ApiModelProperty(value = "申请开票人,购买方")
    @NotNull(message = "申请开票人不能为空")
    private String applicationInvoicePerson;

    @ApiModelProperty(value = "发票金额")
    @NotNull(message = "发票金额不能为空")
    private BigDecimal invoiceMoney;

    @ApiModelProperty(value = "开票类目")
    @NotNull(message = "开票类目不能为空")
    private String invoiceCatalog;

    @ApiModelProperty(value = "发票")
    @NotNull(message = "发票不能为空")
    private String invoiceUrl;

    @ApiModelProperty(value = "税票")
    private String taxReceiptUrl;

    @ApiModelProperty(value = "快递单号")
    @NotNull(message = "快递单号不能为空")
    private String expressSheetNo;

    @ApiModelProperty(value = "快递公司名称")
    @NotNull(message = "快递公司名称不能为空")
    private String expressCompanyName;

    @ApiModelProperty(value = "开票说明")
    private String invoiceDesc;

}