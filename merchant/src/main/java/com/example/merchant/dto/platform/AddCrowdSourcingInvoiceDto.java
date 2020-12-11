package com.example.merchant.dto.platform;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/9
 */
@Data
public class AddCrowdSourcingInvoiceDto {

    /**
     * id
     */
    private String id;

    /**
     * 众包支付id
     */
    private String paymentOrderManyId;

    /**
     * 申请id
     */
    @ApiModelProperty("申请id:可以为空，为空则说明商户未申请")
    private String applicationId;
    /**
     * 发票数字
     */
    @Pattern(regexp = "^[0-9]*$", message = "必须为数字")
    @ApiModelProperty("发票数字")
    @NotNull(message = "发票数字不能为空")
    private String invoiceNumber;

    /**
     * 发票代码
     */
    @ApiModelProperty("发票代码")
    @Pattern(regexp = "^[0-9]*$", message = "必须为数字")
    @NotNull(message = "发票代码不能为空")
    @TableField("invoice_codeNo")
    private String invoiceCodeno;

    /**
     * 购买方
     */
    @NotNull(message = "购买方不能为空")
    @ApiModelProperty("购买方")
    private String invoicePrintPerson;

    /**
     * 开票金额
     */
    @ApiModelProperty("开票金额")
    @NotNull(message = "开票金额不能为空")
    private BigDecimal invoiceMoney;

    /**
     * 开票类目
     */
    @ApiModelProperty("开票类目")
    @NotNull(message = "开票类目不能为空")
    private String invoiceCatalogId;

    /**
     * 发票Url
     */
    @ApiModelProperty("发票Url")
    @NotNull(message = "发票Url不能为空")
    private String invoiceUrl;

    /**
     * 税票Url
     */
    @ApiModelProperty("税票Url")
    @NotNull(message = "税票Url不能为空")
    private String taxReceiptUrl;

    /**
     * 快递单号
     */
    @ApiModelProperty("快递单号")
    @NotNull(message = "快递单号不能为空")
    private String expressSheetNo;

    /**
     * 快递公司
     */
    @ApiModelProperty("快递公司")
    @NotNull(message = "快递公司不能为空")
    private String expressCompanyName;

    private String invoiceDesc;
}
