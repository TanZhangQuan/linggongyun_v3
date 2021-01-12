package com.example.merchant.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXX")
public class MakerInvoiceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "门征单开主键")
    private String id;

    @ApiModelProperty(value = "创客支付ID")
    @NotNull(message = "创客支付ID，不能为空")
    private String paymentInventoryId;

    @ApiModelProperty(value = "服务名称")
    @NotNull(message = "服务名称不能为空")
    private String invoiceCategory;

    @ApiModelProperty(value = "开票金额")
    @NotNull(message = "开票金额不能为空")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "税额合计")
    @NotNull(message = "税额合计不能为空")
    private BigDecimal taxAmount;

    @ApiModelProperty(value = "开票人")
    @NotNull(message = "开票人不能为空")
    private String ivoicePerson;

    @ApiModelProperty(value = "销售方名称")
    @NotNull(message = "销售方名称不能为空")
    private String saleCompany;

    @ApiModelProperty(value = "发票")
    private String makerVoiceUrl;

    @ApiModelProperty(value = "税票")
    private String makerTaxUrl;

}
