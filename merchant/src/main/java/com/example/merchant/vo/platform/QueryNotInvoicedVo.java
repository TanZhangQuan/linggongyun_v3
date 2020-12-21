package com.example.merchant.vo.platform;

import com.example.merchant.vo.merchant.InvoiceCatalogVo;
import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.PaymentOrderManyVO;
import lombok.Data;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/10
 */
@Data
public class QueryNotInvoicedVo {
    /**
     * 众包支付信息
     */
    private PaymentOrderManyVO paymentOrderManyVo;

    /**
     * 购买方
     */
    private BuyerVO buyerVo;

    /**
     * 开票类目
     */
    private InvoiceCatalogVo invoiceCatalogVo;

    /**
     * 地址信息
     */
    private AddressVo addressVo;

    /**
     * 备注
     */
    private String remarks;
}
