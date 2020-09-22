package com.example.mybatis.vo;

import lombok.Data;

/**
 * 平台端发票申请信息
 */
@Data
public class PlaInvoiceInfoVo {

    /**
     * 开票类目
     */
    private String invoiceCatalogType;

    /**
     * 申请说明
     */
    private String applicationDesc;

    /**
     * 申请地址
     */
    private String applicationAddress;
    
}
