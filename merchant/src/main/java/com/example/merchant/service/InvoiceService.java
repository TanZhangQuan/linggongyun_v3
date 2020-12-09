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

    ReturnJson selectTobeinvoiced(QueryTobeinvoicedDto queryTobeinvoicedDto,String merchantId);

    ReturnJson  getInvoiceList(QueryTobeinvoicedDto queryTobeinvoicedDto,String merchantId);

    ReturnJson  getInvInfoById(String invId,String merchantId);

    ReturnJson isServiceProvider(String serviceProviderNames);
    /**
     * 平台端
     */
    //平台查询待开票总包数据
    ReturnJson getPlaInvoiceList(TobeinvoicedDto tobeinvoicedDto);

    //平台查询发票信息
    ReturnJson getPlaInvoiceInfo(String applicationId);

    //创建发票信息
    ReturnJson saveInvoice(AddInvoiceDto addInvoiceDto);

    String getInvoiceCode();

    ReturnJson getListInvoicequery(TobeinvoicedDto tobeinvoicedDto);

    ReturnJson updateInvoiceById(AddInvoiceDto addInvoiceDto);

    ReturnJson getListSubQuery(TobeinvoicedDto tobeinvoicedDto);

    ReturnJson getInvoiceListQuery(String invoiceId,String companySNames,String platformServiceProviders);

    /**
     * 总包开票编辑详情
     *
     * @param invoiceId
     * @return
     */
    ReturnJson queryInvoice(String invoiceId);
}
