package com.example.merchant.dto.platform;

import lombok.Data;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/2
 */
@Data
public class AddInvoiceCatalogDto {

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
