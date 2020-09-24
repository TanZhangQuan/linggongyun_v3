package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.ApplicationCrowdSourcing;
import com.example.mybatis.entity.CrowdSourcingInvoice;

public interface CrowdSourcingInvoiceService {
    ReturnJson addCrowdSourcingInvoice(ApplicationCrowdSourcing applicationCrowdSourcing);

    ReturnJson getCrowdSourcingInfo(TobeinvoicedDto tobeinvoicedDto);

    ReturnJson getInvoiceById(String csiId);

    ReturnJson getTobeCrowdSourcingInvoice(TobeinvoicedDto tobeinvoicedDto);

    ReturnJson getPaymentOrderMany(String payId);

    ReturnJson getPaymentInventoryPass(String payId);

    ReturnJson getBuyer(String id);

    ReturnJson getApplicationInfo(String applicationId);

    ReturnJson saveCrowdSourcingInvoice(CrowdSourcingInvoice crowdSourcingInvoice);
}
