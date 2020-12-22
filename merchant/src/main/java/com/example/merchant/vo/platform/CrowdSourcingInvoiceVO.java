package com.example.merchant.vo.platform;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/10
 */
@Data
public class CrowdSourcingInvoiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 众包ID
     */
    private String id;

    /**
     * 发票数字
     */
    private String invoiceNumber;

    /**
     * 发票代码
     */
    private String invoiceCodeno;

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
     * 快递公司
     */
    private String expressCompanyName;

    /**
     * 开票说明
     */
    private String invoiceDesc;
}
