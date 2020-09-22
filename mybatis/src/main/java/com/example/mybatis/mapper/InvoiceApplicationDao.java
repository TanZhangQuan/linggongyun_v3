package com.example.mybatis.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.dto.ApplicationPaymentDto;
import com.example.mybatis.dto.InvoiceApplicationDto;
import com.example.mybatis.entity.InvoiceApplication;

public interface InvoiceApplicationDao extends BaseMapper<InvoiceApplication> {

    int addInvApplication(InvoiceApplicationDto invoiceApplicationDto);

    int addApplicationPay(ApplicationPaymentDto applicationPaymentDto);
}
