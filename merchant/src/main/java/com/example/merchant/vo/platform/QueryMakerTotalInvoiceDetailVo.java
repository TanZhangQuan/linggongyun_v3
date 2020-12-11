package com.example.merchant.vo.platform;

import com.example.merchant.vo.merchant.InvoiceCatalogVo;
import com.example.mybatis.vo.BuyerVo;
import com.example.mybatis.vo.PaymentOrderVo;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/11
 */
@Data
public class QueryMakerTotalInvoiceDetailVo {
    /**
     * 支付信息
     */
    private List<PaymentOrderVo> paymentOrderVoList;

    /**
     * 购买信息
     */
    private BuyerVo queryBuyer;

    /**
     * 汇总代开发票信息
     */
    private MakerTotalInvoiceDetailsVo makerTotalInvoiceDetails;

    /**
     * 开票类目信息
     */
    private InvoiceCatalogVo invoiceCatalogVo;
}
