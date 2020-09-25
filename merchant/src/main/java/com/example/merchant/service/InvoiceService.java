package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.mybatis.dto.AddInvoiceDto;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.Invoice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 发票相关 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface InvoiceService extends IService<Invoice> {

    ReturnJson selectTobeinvoiced(TobeinvoicedDto tobeinvoicedDto);

    ReturnJson  getInvoiceList(TobeinvoicedDto tobeinvoicedDto);

    ReturnJson  getInvInfoById(String invId);

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
}
