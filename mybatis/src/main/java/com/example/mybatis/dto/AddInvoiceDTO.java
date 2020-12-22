package com.example.mybatis.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(value = "总包开票")
public class AddInvoiceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 总包ID
     */
    private String id;

    /**
     * 申请开票ID
     */
    @NotNull(message = "申请开票id不能为空")
    private String applicationId;

    /**
     * 发票代码
     */
    @NotNull(message = "发票数字不能为空")
    private String invoiceNumber;

    /**
     * 发票代码
     */
    @NotNull(message = "发票代码不能为空")
    private String invoiceCodeNo;

    /**
     * 开票人,销售方
     */
    @NotNull(message = "开票人不能为空")
    private String invoicePrintPerson;

    /**
     * 申请开票人,购买方
     */
    @NotNull(message = "申请开票人不能为空")
    private String applicationInvoicePerson;

    /**
     * 发票张数
     */
    private Integer invoiceNumbers = 1;

    /**
     * 发票金额
     */
    @NotNull(message = "发票金额不能为空")
    private BigDecimal invoiceMoney;

    /**
     * 开票类目
     */
    @NotNull(message = "开票类目不能为空")
    private String invoiceCatalog;

    /**
     * 发票
     */
    @NotNull(message = "发票不能为空")
    private String invoiceUrl;

    /**
     * 税票
     */
    @NotNull(message = "税票url不能为空")
    private String taxReceiptUrl;

    /**
     * 快递单号
     */
    @NotNull(message = "快递单号不能为空")
    private String expressSheetNo;

    /**
     * 快递公司名称
     */
    @NotNull(message = "快递公司名称不能为空")
    private String expressCompanyName;

    /**
     * 开票说明
     */
    private String invoiceDesc;

}