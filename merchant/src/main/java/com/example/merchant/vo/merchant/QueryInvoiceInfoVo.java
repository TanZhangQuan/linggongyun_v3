package com.example.merchant.vo.merchant;

import com.example.common.util.ExpressLogisticsInfo;
import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.PaymentOrderManyVO;
import com.example.mybatis.vo.SendAndReceiveVO;
import lombok.Data;

import java.util.List;

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
    private PaymentOrderManyVO paymentOrderManyVo;

    /**
     * 销售方信息
     */
    private BuyerVO buyerVo;

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
     * 发收件信息
     */
    private SendAndReceiveVO sendAndReceiveVo;

    /**
     * 物流信息
     */
    private List<ExpressLogisticsInfo> expressLogisticsInfoList;
}
