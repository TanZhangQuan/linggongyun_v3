package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.MakerInvoice;


public interface MakerInvoiceService extends IService<MakerInvoice> {
    ReturnJson getPaymentInventory(String invoiceId);

    ReturnJson saveOrUpdateMakerInvoice(MakerInvoice makerInvoice);
}
