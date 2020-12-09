package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.mybatis.dto.QuerySubpackageDto;
import com.example.mybatis.dto.TobeinvoicedDto;

public interface SubpackageService {

    ReturnJson getSummaryInfo(String id,String merchantId);

    ReturnJson getSummaryList(QuerySubpackageDto querySubpackageDto, String merchantId);

    ReturnJson getSummary(String invoiceId);

    ReturnJson getSubpackageInfoById(String invoiceId);

    ReturnJson getListByInvoiceId(String invoiceId, Integer pageNo, Integer pageSize);
}
