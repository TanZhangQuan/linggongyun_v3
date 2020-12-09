package com.example.merchant.vo.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/8
 */
@Data
public class PlaInvoiceInfoVo {
    @ApiModelProperty(value = "修改时用")
    private String id;

    @ApiModelProperty(value = "申请开票id")
    private String applicationId;

    @ApiModelProperty(value = "发票数字")
    private String invoiceNumber;

    @ApiModelProperty(value = "发票代码")
    private String invoiceCodeNo;

    @ApiModelProperty(value = "开票人,销售方")
    private String invoicePrintPerson;

    @ApiModelProperty(value = "申请开票人,购买方")
    private String applicationInvoicePerson;

    @ApiModelProperty(value = "发票张数,当前默认为1")
    private Integer invoiceNumbers = 1;

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
