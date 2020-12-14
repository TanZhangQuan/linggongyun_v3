package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.mybatis.dto.QueryTobeinvoicedDto;
import com.example.mybatis.dto.AddInvoiceDto;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.Invoice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 发票相关 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface InvoiceService extends IService<Invoice> {

    /**
     * 查询待开票信息
     *
     * @param queryTobeinvoicedDto
     * @param merchantId
     * @return
     */
    ReturnJson selectTobeinvoiced(QueryTobeinvoicedDto queryTobeinvoicedDto, String merchantId);

    /**
     * 总包发票列表,已开票
     *
     * @param queryTobeinvoicedDto
     * @param merchantId
     * @return
     */
    ReturnJson getInvoiceList(QueryTobeinvoicedDto queryTobeinvoicedDto, String merchantId);

    /**
     * 总包发票列表已开票,发票信息
     *
     * @param invId
     * @param merchantId
     * @return
     */
    ReturnJson getInvInfoById(String invId, String merchantId);

    /**
     * 判断是否为同一服务商,同时传入多个name
     *
     * @param serviceProviderNames
     * @return
     */
    ReturnJson isServiceProvider(String serviceProviderNames);
    /**
     * 平台端
     */
    /**
     * 平台查询待开票总包数据
     *
     * @param tobeinvoicedDto
     * @return
     */
    ReturnJson getPlaInvoiceList(TobeinvoicedDto tobeinvoicedDto);

    /**
     * 平台查询发票信息
     *
     * @param applicationId
     * @return
     */
    ReturnJson getPlaInvoiceInfo(String applicationId);

    /**
     * 创建发票信息
     *
     * @param addInvoiceDto
     * @return
     */
    ReturnJson saveInvoice(AddInvoiceDto addInvoiceDto);

    /**
     * 发票编号
     *
     * @return
     */
    String getInvoiceCode();

    /**
     * 总包开票，以开票
     *
     * @param tobeinvoicedDto
     * @return
     */
    ReturnJson getListInvoicequery(TobeinvoicedDto tobeinvoicedDto);

    ReturnJson updateInvoiceById(AddInvoiceDto addInvoiceDto);

    /**
     * 分包开票，待开票
     *
     * @param tobeinvoicedDto
     * @return
     */
    ReturnJson getListSubQuery(TobeinvoicedDto tobeinvoicedDto);

    /**
     * 汇总开票详情数据
     *
     * @param invoiceId
     * @return
     */
    ReturnJson getInvoiceListQuery(String invoiceId);

    /**
     * 总包开票编辑详情
     *
     * @param invoiceId
     * @return
     */
    ReturnJson queryInvoice(String invoiceId);
}
