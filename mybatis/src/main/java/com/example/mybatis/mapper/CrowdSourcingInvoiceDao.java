package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.dto.QueryCrowdSourcingDto;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.ApplicationCrowdSourcing;
import com.example.mybatis.entity.CrowdSourcingInvoice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.po.InvoiceInfoPO;
import com.example.mybatis.po.InvoicePO;
import com.example.mybatis.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-21
 */
public interface CrowdSourcingInvoiceDao extends BaseMapper<CrowdSourcingInvoice> {
    InvoicePO selectCrowdInvoiceMoney(String merchantId);
    InvoicePO selectCrowdInvoiceMoneyPaas(List<String> merchantId);
    int addCrowdSourcingInvoice(ApplicationCrowdSourcing applicationCrowdSourcing);

    IPage<CrowdSourcingInfoVo> getCrowdSourcingInfo(Page page, QueryCrowdSourcingDto queryCrowdSourcingDto, String userId);

    //众包发票id
    InvoiceInformationVo getInvoiceById(String csiId);

    InvoicePO selectCrowdInvoiceMoneyPaasTax(String taxId);

    InvoicePO selectCrowdInvoiceMoneyPaasRegultor(@Param("taxIds") List<String> taxIds);

    //购买方id
    BuyerVo getBuyer(String id);

    /** ----平台端---- **/
    IPage<CrowdSourcingInvoiceInfoVo> getCrowdSourcingInvoicePass(Page page, @Param("tobeinvoicedDto") TobeinvoicedDto tobeinvoicedDto);

    PaymentOrderManyVo getPaymentOrderManyPass(String appcationId);

    List<InvoiceDetailsVo> getPaymentInventoryPass(String payId);

    String getCrowdInvoiceCode();

    IPage<CrowdSourcingInfoVo> getCrowdSourcingInfoPass(Page page,@Param("tobeinvoicedDto") TobeinvoicedDto tobeinvoicedDto);

    IPage<InvoiceDetailsVo> getPaymentInventoryInfoPass(Page page,String invoiceId);

    //根据支付订单ID查找发票信息
    InvoiceInfoPO selectInvoiceInfoPO(String paymentOrderId);

    PaymentOrderManyVo getPaymentOrderManySPass(String invoiceId);

    SendAndReceiveVo querySendAndReceive(String invoiceId);
}
