package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.AddApplicationCrowdSourcingDto;
import com.example.merchant.dto.platform.AddCrowdSourcingInvoiceDto;
import com.example.mybatis.dto.QueryCrowdSourcingDto;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.ApplicationCrowdSourcing;
import com.example.mybatis.entity.CrowdSourcingInvoice;

public interface CrowdSourcingInvoiceService {
    ReturnJson addCrowdSourcingInvoice(AddApplicationCrowdSourcingDto addApplicationCrowdSourcingDto);

    ReturnJson getCrowdSourcingInfo(QueryCrowdSourcingDto queryCrowdSourcingDto, String userId);

    ReturnJson getInvoiceById(String csiId);

    ReturnJson getTobeCrowdSourcingInvoice(TobeinvoicedDto tobeinvoicedDto);

    ReturnJson getPaymentOrderMany(String payId);

    ReturnJson getPaymentInventoryPass(String payId);

    ReturnJson getBuyer(String id);

    ReturnJson getApplicationInfo(String applicationId);

    /**
     * 众包开票
     *
     * @param addCrowdSourcingInvoiceDto
     * @return
     */
    ReturnJson saveCrowdSourcingInvoice(AddCrowdSourcingInvoiceDto addCrowdSourcingInvoiceDto);

    ReturnJson getCrowdSourcingInfoPass(TobeinvoicedDto tobeinvoicedDto);

    ReturnJson getPaymentInventoryInfoPass(String invoiceId);

    /**
     * 查看申请开票详情信息
     *
     * @param applicationId
     * @return
     */
    ReturnJson queryApplicationInfo(String applicationId, String merchantId);

    /**
     * 查看已开票的发票信息
     *
     * @param invoiceId
     * @return
     */
    ReturnJson queryInvoiceInfo(String invoiceId, String merchantId);


}
