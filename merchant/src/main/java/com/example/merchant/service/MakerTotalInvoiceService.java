package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.MakerTotalInvoiceDTO;
import com.example.mybatis.dto.QueryMakerTotalInvoiceDTO;
import com.example.mybatis.entity.MakerTotalInvoice;

public interface MakerTotalInvoiceService extends IService<MakerTotalInvoice> {

    /**
     * 汇总代开已开票列表
     *
     * @param makerTotalInvoiceDto
     * @param managerId
     * @return
     */
    ReturnJson saveOrUpdateMakerTotalInvoice(MakerTotalInvoiceDTO makerTotalInvoiceDto, String managerId);

    /**
     * 查询汇总代开显示详情
     *
     * @param invoiceIds
     * @return
     */
    ReturnJson queryMakerTotalInvoiceInfo(String invoiceIds);

    /**
     * 汇总代开已开票
     *
     * @param queryMakerTotalInvoiceDto
     * @return
     */
    ReturnJson queryMakerTotalInvoice(QueryMakerTotalInvoiceDTO queryMakerTotalInvoiceDto);

    /**
     * 汇总代开已开票详情
     *
     * @param invoiceId
     * @return
     */
    ReturnJson queryMakerTotalInvoiceDetails(String invoiceId);

    /**
     * 汇总代开详情，支付明细列表
     *
     * @param invoiceId
     * @param pageNo
     * @param pageSize
     * @return
     */
    ReturnJson getMakerTotalInvoicePayList(String invoiceId, Integer pageNo, Integer pageSize);
}
