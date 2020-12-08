package com.example.mybatis.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel(value = "总包开票")
public class AddInvoiceDto {

    @ApiModelProperty(value = "修改时用")
    private String id;

    @NotNull(message = "申请开票id不能为空")
    @ApiModelProperty(value = "申请开票id")
    private String applicationId;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "开票时间,默认当前系统时间")
    private String invoicePrintDate;

    @NotNull(message = "发票代码不能为空")
    @ApiModelProperty(value = "发票数字")
    private String invoiceNumber;

    @NotNull(message = "发票代码不能为空")
    @ApiModelProperty(value = "发票代码")
    private String invoiceCodeNo;

    @NotNull(message = "开票人不能为空")
    @ApiModelProperty(value = "开票人,销售方")
    private String invoicePrintPerson;

    @NotNull(message = "申请开票人不能为空")
    @ApiModelProperty(value = "申请开票人,购买方")
    private String applicationInvoicePerson;

    @ApiModelProperty(value = "发票张数,当前默认为1")
    private Integer invoiceNumbers=1;

    @NotNull(message = "发票金额不能为空")
    @ApiModelProperty(value = "发票金额")
    private BigDecimal invoiceMoney;

    @NotNull(message = "开票类目不能为空")
    @ApiModelProperty(value = "开票类目")
    private String invoiceCatalog;

    @NotNull(message = "发票url不能为空")
    @ApiModelProperty(value = "发票存放url")
    private String invoiceUrl;

    @NotNull(message = "税票url不能为空")
    @ApiModelProperty(value = "税票存放url")
    private String taxReceiptUrl;

    @NotNull(message = "快递单号不能为空")
    @ApiModelProperty(value = "快递单号")
    private String expressSheetNo;

    @NotNull(message = "快递公司名称不能为空")
    @ApiModelProperty(value = "快递公司名称")
    private String expressCompanyName;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "快递更新时间")
    private String expressUpdateDatetime;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "快递更新人员")
    private String expressUpdatePerson;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "快递更新人员电话")
    private String expressUpdatePersonTel;

    @ApiModelProperty(value = "开票说明")
    private String invoiceDesc;

}