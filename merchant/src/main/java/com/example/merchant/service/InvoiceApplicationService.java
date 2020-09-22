package com.example.merchant.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.mybatis.dto.ApplicationPaymentDto;
import com.example.mybatis.dto.InvoiceApplicationDto;
import com.example.mybatis.entity.InvoiceApplication;

public interface InvoiceApplicationService extends IService<InvoiceApplication> {
    int updateById(String applicationId, Integer state);
    //添加开票申请
    ReturnJson addInvApplication(InvoiceApplicationDto invoiceApplicationDto);

    //支付&申请对应
    int addApplicationPay(ApplicationPaymentDto applicationPaymentDto);
}
