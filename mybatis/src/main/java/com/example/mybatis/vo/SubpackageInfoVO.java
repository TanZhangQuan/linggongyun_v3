package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubpackageInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 开票类目
     */
    private String invoiceCategory;

    /**
     * 备注说明
     */
    private String makerInvoiceDesc;

    /**
     * 发票地址
     */
    private String makerInvoiceUrl;

    /**
     * 税票地址
     */
    private String makerTaxUrl;
}
