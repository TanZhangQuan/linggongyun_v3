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

    @NotNull(message = "不能为空")
    @ApiModelProperty(value = "发票代码")
    private String invoiceTypeNo;

    @NotNull(message = "不能为空")
    @ApiModelProperty(value = "发票号码")
    private String invoiceSerialNo;

    @ApiModelProperty(value = "开票日期")
    private LocalDateTime invoiceDate;

    @NotNull(message = "服务类型不能为空")
    @ApiModelProperty(value = "服务类型")
    private String invoiceCategory;

    @NotNull(message = "价税合计不能为空")
    @ApiModelProperty(value = "价税合计")
    private BigDecimal totalAmount;

    @NotNull(message = "税额总价不能为空")
    @ApiModelProperty(value = "税额,所有税额以','隔开")
    private String taxAmount;

    @NotNull(message = "开票人不能为空")
    @ApiModelProperty(value = "开票人")
    private String invoicePerson;

    @NotNull(message = "销售方名称不能为空")
    @ApiModelProperty(value = "销售方名称,税务局")
    private String saleCompany;

    @ApiModelProperty(value = "开票说明")
    private String makerInvoiceDesc;

    @NotNull(message = "分包发票url不能为空")
    @ApiModelProperty(value = "分包发票url")
    private String makerInvoiceUrl;

    @NotNull(message = "分包完税证明URL不能为空")
    @ApiModelProperty(value = "分包完税证明URL")
    private String makerTaxUrl;

    @ApiModelProperty(value = "发票上传日期")
    private LocalDateTime makerVoiceUploadDateTime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;
}
