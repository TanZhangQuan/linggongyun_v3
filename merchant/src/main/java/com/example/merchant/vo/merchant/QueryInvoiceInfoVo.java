package com.example.merchant.vo.merchant;

import com.example.mybatis.vo.BuyerVo;
import com.example.mybatis.vo.PaymentOrderManyVo;
import lombok.Data;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/7
 */
@Data
public class QueryInvoiceInfoVo {
    /**
     * 支付信息
     */
    private PaymentOrderManyVo paymentOrderManyVo;

    /**
     * 销售方信息
     */
    private BuyerVo buyerVo;

    /**
     * 申请开票信息
     */
    private InvoiceApplicationVo invoiceApplicationVo;

    /**
     * 开票类目
     */
    private InvoiceCatalogVo invoiceCatalogVo;

    /**
     * 发票信息
     */
    private InvoiceVo invoiceVo;

    /**
     * 物流信息
     */
    private SendAndReceiveVo sendAndReceiveVo;
}
