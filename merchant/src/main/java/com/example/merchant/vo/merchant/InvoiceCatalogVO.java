package com.example.merchant.vo.merchant;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/4
 */
@Data
public class InvoiceCatalogVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 开票类目ID
     */
    private String id;
    /**
     * 服务类型
     */
    private String serviceType;

    /**
     * 具体服务内容
     */
    private String serviceContent;

    /**
     * 开票类目
     */
    private String billingCategory;
}
