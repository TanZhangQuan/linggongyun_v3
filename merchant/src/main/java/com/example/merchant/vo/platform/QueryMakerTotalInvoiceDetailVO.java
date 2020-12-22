package com.example.merchant.vo.platform;

import com.example.merchant.vo.merchant.InvoiceCatalogVO;
import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.PaymentOrderVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/11
 */
@Data
public class QueryMakerTotalInvoiceDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付信息
     */
    private List<PaymentOrderVO> paymentOrderVOList;

    /**
     * 购买信息
     */
    private BuyerVO queryBuyer;

    /**
     * 汇总代开发票信息
     */
    private MakerTotalInvoiceDetailsVO makerTotalInvoiceDetails;

    /**
     * 开票类目信息
     */
    private InvoiceCatalogVO invoiceCatalogVo;
}
