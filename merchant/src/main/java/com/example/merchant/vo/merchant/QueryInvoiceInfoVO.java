package com.example.merchant.vo.merchant;

import com.example.common.util.ExpressLogisticsInfo;
import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.PaymentOrderManyVO;
import com.example.mybatis.vo.SendAndReceiveVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/7
 */
@Data
public class QueryInvoiceInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

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
    private InvoiceApplicationVO invoiceApplicationVo;

    /**
     * 开票类目
     */
    private InvoiceCatalogVO invoiceCatalogVo;

    /**
     * 发票信息
     */
    private InvoiceVO invoiceVo;

    /**
     * 发收件信息
     */
    private SendAndReceiveVO sendAndReceiveVo;

    /**
     * 物流信息
     */
    private List<ExpressLogisticsInfo> expressLogisticsInfoList;
}
