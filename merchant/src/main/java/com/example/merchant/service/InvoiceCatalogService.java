package com.example.merchant.service;

import com.example.common.util.ReturnJson;

public interface InvoiceCatalogService {
    /**
     * 根据商户id查询商户对应的开票类目
     *
     * @param id
     * @param packageStatus
     * @return
     */
    ReturnJson getListInv(String id, Integer packageStatus);
}
