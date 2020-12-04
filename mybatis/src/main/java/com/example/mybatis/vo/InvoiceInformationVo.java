package com.example.mybatis.vo;

import lombok.Data;

/**
 * 发票信息Vo
 */
@Data
public class InvoiceInformationVo {

    /**
     * 申请开票ID
     */
    private String invAppId;
    /**
     * 开票类目
     */
    private String invoiceCatalogType;
    /**
     * 开票申请说明
     */
    private String applicationDesc;
    /**
     * 开票地址
     */
    private String applicationAddress;
    /**
     * 发票Url
     */
    private String invoiceUrl;
    /**
     * 税票Url
     */
    private String taxReceiptUrl;
    /**
     * 快递公司
     */
    private String expressCompanyName;
    /**
     * 快递单号
     */
    private String expressSheetNo;
}
