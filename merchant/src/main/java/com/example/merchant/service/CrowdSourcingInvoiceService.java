package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.AddApplicationCrowdSourcingDTO;
import com.example.merchant.dto.platform.AddCrowdSourcingInvoiceDTO;
import com.example.merchant.exception.CommonException;
import com.example.mybatis.dto.QueryCrowdSourcingDTO;
import com.example.mybatis.dto.TobeInvoicedDTO;

public interface CrowdSourcingInvoiceService {
    /**
     * 众包开票申请
     *
     * @param addApplicationCrowdSourcingDto
     * @return
     */
    ReturnJson addCrowdSourcingInvoice(AddApplicationCrowdSourcingDTO addApplicationCrowdSourcingDto) throws CommonException;

    /**
     * 众包发票详情信息
     *
     * @param queryCrowdSourcingDto
     * @param userId
     * @return
     */
    ReturnJson getCrowdSourcingInfo(QueryCrowdSourcingDTO queryCrowdSourcingDto, String userId);

    /**
     * 查询单条众包信息
     *
     * @param csiId
     * @return
     */
    ReturnJson getInvoiceById(String csiId);

    /**
     * 平台端查询众包待开票
     *
     * @param tobeinvoicedDto
     * @return
     */
    ReturnJson getTobeCrowdSourcingInvoice(TobeInvoicedDTO tobeinvoicedDto);

    /**
     * 查询众包支付信息
     * @param payId
     * @return
     */
    ReturnJson getPaymentOrderMany(String payId);

    /**
     * 查询众包支付明细
     * @param invoiceId
     * @param pageNo
     * @param pageSize
     * @return
     */
    ReturnJson getPaymentInventoryPass(String invoiceId,Integer pageNo,Integer pageSize);

    /**
     * 购买方
     * @param id
     * @return
     */
    ReturnJson getBuyer(String id);

    /**
     * 众包申请开票信息查询
     *
     * @param applicationId
     * @return
     */
    ReturnJson getApplicationInfo(String applicationId);

    /**
     * 众包开票
     *
     * @param addCrowdSourcingInvoiceDto
     * @return
     */
    ReturnJson saveCrowdSourcingInvoice(AddCrowdSourcingInvoiceDTO addCrowdSourcingInvoiceDto);

    /**
     * 平台端查询众包已开票
     *
     * @param tobeinvoicedDto
     * @return
     */
    ReturnJson getCrowdSourcingInfoPass(TobeInvoicedDTO tobeinvoicedDto);

    /**
     * 平台端众包支付明细列表
     * @param invoiceId
     * @param pageNo
     * @param pageSize
     * @return
     */
    ReturnJson getPaymentInventoryInfoPass(String invoiceId,Integer pageNo,Integer pageSize);

    /**
     * 查看申请开票详情信息
     *
     * @param applicationId
     * @return
     */
    ReturnJson queryApplicationInfo(String applicationId, String merchantId) throws CommonException;

    /**
     * 查看已开票的发票信息
     *
     * @param invoiceId
     * @return
     */
    ReturnJson queryInvoiceInfo(String invoiceId, String merchantId);

    /**
     * 未开票信息
     *
     * @param applicationId
     * @return
     */
    ReturnJson queryNotInvoiced(String applicationId);

    /**
     * 已开票信息
     *
     * @param invoiceId
     * @return
     */
    ReturnJson queryInvoiced(String invoiceId);
}
