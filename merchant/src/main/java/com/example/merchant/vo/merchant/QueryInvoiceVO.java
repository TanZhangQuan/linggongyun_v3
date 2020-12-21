package com.example.merchant.vo.merchant;

import com.example.common.util.ExpressLogisticsInfo;
import com.example.mybatis.vo.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/3
 */
@Data
public class QueryInvoiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付信息
     */
    private List<PaymentOrderVO> paymentOrderVOList;

    /**
     * 开票信息
     */
    private List<BillingInfoVO> billingInfoVOList;

    /**
     * 购买方信息
     */
    private BuyerVO buyerVo;

    /**
     * 销售方信息
     */
    private SellerVO sellerVo;

    /**
     * 开票类目
     */
    private InvoiceCatalogVO invoiceCatalogVo;

    /**
     * 发票信息
     */
    private InvoiceApplicationVO invoiceApplicationVo;

    /**
     * 物流信息
     */
    private List<ExpressLogisticsInfo> expressLogisticsInfoList;

    /**
     * 收发件信息
     */
    private SendAndReceiveVO sendAndReceiveVo;
}
