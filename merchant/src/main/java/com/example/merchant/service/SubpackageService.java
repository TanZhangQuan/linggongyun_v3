package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.mybatis.dto.QuerySubpackageDTO;

public interface SubpackageService {

    /**
     * 汇总代开,详细信息
     *
     * @param id
     * @param merchantId
     * @return
     */
    ReturnJson getSummaryInfo(String id, String merchantId);

    /**
     * 汇总代开已开票
     *
     * @param querySubpackageDto
     * @return
     */
    ReturnJson getSummaryList(QuerySubpackageDTO querySubpackageDto, String merchantId);

    /**
     * 汇总代开,支付信息，税价总和
     *
     * @param invoiceId
     * @return
     */
    ReturnJson getSummary(String invoiceId);

    /**
     * 汇总代开,发票信息
     *
     * @param invoiceId
     * @return
     */
    ReturnJson getSubpackageInfoById(String invoiceId);

    /**
     * 汇总代开,发票信息,创客到手明细
     *
     * @param invoiceId
     * @param pageNo
     * @param pageSize
     * @return
     */
    ReturnJson getListByInvoiceId(String invoiceId, Integer pageNo, Integer pageSize);
}
