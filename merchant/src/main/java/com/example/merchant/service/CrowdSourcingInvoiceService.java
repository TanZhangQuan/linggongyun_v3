package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.ApplicationCrowdSourcing;

public interface CrowdSourcingInvoiceService {
    ReturnJson addCrowdSourcingInvoice(ApplicationCrowdSourcing applicationCrowdSourcing);

    ReturnJson getCrowdSourcingInfo(TobeinvoicedDto tobeinvoicedDto);

    ReturnJson getInvoiceById(String csiId);
}
