package com.example.merchant.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.UpdateApplication;
import com.example.mybatis.dto.ApplicationPaymentDto;
import com.example.mybatis.dto.InvoiceApplicationDto;
import com.example.mybatis.entity.InvoiceApplication;

public interface InvoiceApplicationService extends IService<InvoiceApplication> {
    int updateById(String applicationId, Integer state);

    /**
     * 添加开票申请
     *
     * @param invoiceApplicationDto
     * @return
     */
    ReturnJson addInvApplication(InvoiceApplicationDto invoiceApplicationDto);

    /**
     * 支付&申请对应
     *
     * @param applicationPaymentDto
     * @return
     */
    int addApplicationPay(ApplicationPaymentDto applicationPaymentDto);

    /**
     * 去申请开票信息
     *
     * @param payIds
     * @param merchantId
     * @return
     */
    ReturnJson goInvApplication(String payIds, String merchantId);

    /**
     * 修改申请开票信息
     *
     * @param updateApplication
     * @param userId
     * @return
     */
    ReturnJson updateApplication(UpdateApplication updateApplication,String userId);

    /**
     * 查询开票申请
     *
     * @param applicationId
     * @return
     */
    ReturnJson queryApplicationInfo(String applicationId,String userId);

    /**
     * 已开票查看
     *
     * @param invoiceId
     * @return
     */
    ReturnJson queryInvoiceInfo(String invoiceId);
}
