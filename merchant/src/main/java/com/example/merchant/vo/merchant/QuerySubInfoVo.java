package com.example.merchant.vo.merchant;

import com.example.mybatis.vo.BuyerVo;
import com.example.mybatis.vo.PaymentOrderManyVo;
import com.example.mybatis.vo.PaymentOrderVo;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/7
 */
@Data
public class QuerySubInfoVo {
    /**
     * 支付信息
     */
    private List<PaymentOrderVo> paymentOrderVos;

    /**
     * 销售方信息
     */
    private BuyerVo buyerVo;

    /**
     * 开票类目信息
     */
    private InvoiceCatalogVo invoiceCatalogVo;

    /**
     * 发票信息
     */
    private InvoiceInfoVo invoiceInfoVo;

    /**
     * 备注说明
     */
    private String remarks;
}
