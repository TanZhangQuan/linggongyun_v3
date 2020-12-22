package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 平台端发票申请信息
 */
@Data
public class PlaInvoiceInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

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
