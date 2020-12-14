package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.AddInvoiceCatalogDto;
import com.example.merchant.dto.platform.TaxDto;
import com.example.merchant.dto.platform.TaxListDto;
import com.example.merchant.exception.CommonException;
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
    ReturnJson getTaxPaasAll(String companyId, Integer packageStatus);

    ReturnJson getCatalogAll();

    ReturnJson saveCatalog(AddInvoiceCatalogDto addInvoiceCatalogDto);
    ReturnJson saveTax(TaxDto taxDto) throws Exception;

    ReturnJson getTaxList(TaxListDto taxListDto);

    ReturnJson getTaxInfo(String taxId);

    ReturnJson transactionRecordCount(String taxId);

    ReturnJson transactionRecord(String taxId, Integer page, Integer pageSize);

    ReturnJson getSellerById(String id);

    ReturnJson getTaxPaasList();

}
