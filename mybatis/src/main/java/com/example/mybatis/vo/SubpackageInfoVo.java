package com.example.mybatis.vo;

import lombok.Data;

@Data
public class SubpackageInfoVo {

    //开票类目
    private String invoiceCategory;

    //备注说明
    private String makerInvoiceDesc;

    //发票地址
    private String makerInvoiceUrl;
    //税票地址
    private String makerTaxUrl;
}
