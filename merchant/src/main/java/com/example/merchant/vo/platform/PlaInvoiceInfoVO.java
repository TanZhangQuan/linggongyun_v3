package com.example.merchant.vo.platform;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/8
 */
@Data
public class PlaInvoiceInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 总包发票ID
     */
    private String id;

    /**
     * 申请开票ID
     */
    private String applicationId;

    /**
     * 发票数字
     */
    private String invoiceNumber;

    /**
     * 发票代码
     */
    private String invoiceCodeNo;

    /**
     * 开票人,销售方
     */
    private String invoicePrintPerson;

    /**
     * 申请开票人,购买方
     */
    private String applicationInvoicePerson;

    /**
     * 发票张数
     */
    private Integer invoiceNumbers = 1;

    /**
     * 发票金额
     */
    private BigDecimal invoiceMoney;

    /**
     * 开票类目
     */
    private String invoiceCatalog;

    /**
     * 发票
     */
    private String invoiceUrl;

    /**
     * 税票
     */
    private String taxReceiptUrl;

    /**
     * 快递单号
     */
    private String expressSheetNo;

    /**
     * 快递公司名称
     */
    private String expressCompanyName;

    /**
     * 开票说明
     */
    private String invoiceDesc;
}
