package com.example.merchant.vo.platform;

import com.example.merchant.vo.merchant.InvoiceApplicationVO;
import com.example.merchant.vo.merchant.InvoiceCatalogVO;
import com.example.mybatis.vo.BillingInfoVO;
import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.PaymentOrderVO;
import com.example.mybatis.vo.SellerVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/3
 */
@Data
public class QueryApplicationVO implements Serializable {
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
     * 地址信息
     */
    private AddressVO addressVo;
}
