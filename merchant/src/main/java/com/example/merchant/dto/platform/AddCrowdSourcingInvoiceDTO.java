package com.example.merchant.dto.platform;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXX")
public class AddCrowdSourcingInvoiceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "众包ID")
    private String id;

    @ApiModelProperty(value = "XXXXX")
    private String paymentOrderManyId;

    @ApiModelProperty(value = "申请id:可以为空，为空则说明商户未申请")
    private String applicationId;

    @ApiModelProperty(value = "发票数字")
    @NotBlank(message = "发票数字不能为空")
    @Pattern(regexp = "^[0-9]*$", message = "必须为数字")
    private String invoiceNumber;

    @ApiModelProperty(value = "发票代码")
    @Pattern(regexp = "^[0-9]*$", message = "必须为数字")
    @NotBlank(message = "发票代码不能为空")
    @TableField("invoice_codeNo")
    private String invoiceCodeno;

    @NotBlank(message = "购买方不能为空")
    @ApiModelProperty(value = "购买方")
    private String invoicePrintPerson;

    @ApiModelProperty(value = "开票金额")
    @NotNull(message = "开票金额不能为空")
    private BigDecimal invoiceMoney;

    @ApiModelProperty(value = "开票类目")
    @NotBlank(message = "开票类目不能为空")
    private String invoiceCatalogId;

    @ApiModelProperty(value = "发票")
    @NotBlank(message = "发票不能为空")
    private String invoiceUrl;

    @ApiModelProperty(value = "税票")
    @NotBlank(message = "税票不能为空")
    private String taxReceiptUrl;

    @ApiModelProperty(value = "快递单号")
    @NotBlank(message = "快递单号不能为空")
    private String expressSheetNo;

    @ApiModelProperty(value = "快递公司")
    @NotBlank(message = "快递公司不能为空")
    private String expressCompanyName;

    @ApiModelProperty(value = "发票描述")
    private String invoiceDesc;
}
