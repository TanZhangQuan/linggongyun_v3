package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.MakerTotalInvoiceDto;
import com.example.mybatis.entity.MakerTotalInvoice;

public interface MakerTotalInvoiceService  extends IService<MakerTotalInvoice> {

    ReturnJson saveOrUpdateMakerTotalInvoice(MakerTotalInvoiceDto makerTotalInvoiceDto);
}
