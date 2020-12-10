package com.example.merchant.vo.platform;

import com.example.common.util.ExpressLogisticsInfo;
import com.example.merchant.vo.merchant.InvoiceCatalogVo;
import com.example.mybatis.vo.SendAndReceiveVo;
import com.example.mybatis.vo.BillingInfoVo;
import com.example.mybatis.vo.BuyerVo;
import com.example.mybatis.vo.PaymentOrderVo;
import com.example.mybatis.vo.SellerVo;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/8
 */
@Data
public class QueryPlaInvoiceVo {

    /**
     * 支付信息
     */
    private List<PaymentOrderVo> paymentOrderVoList;

    /**
     * 开票信息
     */
    private List<BillingInfoVo> billingInfoVoList;

    /**
     * 购买方信息
     */
    private BuyerVo buyerVo;

    /**
     * 销售方信息
     */
    private SellerVo sellerVo;

    /**
     * 开票类目
     */
    private InvoiceCatalogVo invoiceCatalogVo;

    /**
     * 发票信息
     */
    private PlaInvoiceInfoVo plaInvoiceInfoVo;

    /**
     * 地址信息
     */
    private AddressVo addressVo;

    /**
     * 发收件信息
     */
    private SendAndReceiveVo sendAndReceiveVo;

    /**
     * 物流信息
     */
    private List<ExpressLogisticsInfo> expressLogisticsInfoList;
}
