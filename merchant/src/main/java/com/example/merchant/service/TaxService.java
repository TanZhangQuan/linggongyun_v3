package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.TaxDto;
import com.example.mybatis.entity.Industry;
import com.example.mybatis.entity.InvoiceCatalog;
import com.example.mybatis.entity.Tax;

/**
 * <p>
 * 合作园区信息 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface TaxService extends IService<Tax> {
    ReturnJson getTaxAll(String merchantId, Integer packageStatus);

    ReturnJson getCatalogAll();

    ReturnJson saveCatalog(InvoiceCatalog invoiceCatalog);

    ReturnJson test(Industry industry);

    ReturnJson saveTax(TaxDto taxDto);

    ReturnJson getSellerById(String id);

}
