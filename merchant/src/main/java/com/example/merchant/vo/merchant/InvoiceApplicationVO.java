package com.example.merchant.vo.merchant;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/3
 */
@Data
public class InvoiceApplicationVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 申请开票id
     */
    private String id;

    /**
     * 开票类目
     */
    private String invoiceCatalogType;

    /**
     * 申请说明
     */
    private String applicationDesc;

    /**
     * 地址
     */
    private String applicationAddress;
}
