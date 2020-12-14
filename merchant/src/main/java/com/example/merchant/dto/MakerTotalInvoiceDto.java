package com.example.merchant.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 汇总开票
 */
@Data
@ApiModel
public class MakerTotalInvoiceDto {

    @ApiModelProperty(value = "修改时用")
    private String id;

    @ApiModelProperty(value = "总包发票id,多个以，隔开")
    private String invoiceId;

    @ApiModelProperty(value = "总包支付id,多个以，隔开")
    private String paymentOrderId;

    @ApiModelProperty(value = "发票代码")
    private String invoiceTypeNo;

    @ApiModelProperty(value = "发票号码")
    private String invoiceSerialNo;

    @ApiModelProperty(value = "服务类型")
    private String invoiceCategory;

    @ApiModelProperty(value = "价税合计")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "税额,所有税额以','隔开")
    private String taxAmount;

    @ApiModelProperty(value = "销售方名称,税务局")
    private String saleCompany;

    @ApiModelProperty(value = "开票说明")
    private String makerInvoiceDesc;

    @ApiModelProperty(value = "分包发票url")
    private String makerInvoiceUrl;

    @ApiModelProperty(value = "分包完税证明URL")
    private String makerTaxUrl;
}
