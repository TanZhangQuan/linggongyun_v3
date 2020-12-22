package com.example.mybatis.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.dto.ApplicationPaymentDTO;
import com.example.mybatis.dto.InvoiceApplicationDTO;
import com.example.mybatis.entity.InvoiceApplication;

public interface InvoiceApplicationDao extends BaseMapper<InvoiceApplication> {

    int addInvApplication(InvoiceApplicationDTO invoiceApplicationDto);

    int addApplicationPay(ApplicationPaymentDTO applicationPaymentDto);
}
