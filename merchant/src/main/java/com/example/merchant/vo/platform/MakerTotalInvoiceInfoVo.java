package com.example.merchant.vo.platform;

import com.example.merchant.vo.merchant.InvoiceCatalogVo;
import com.example.mybatis.entity.InvoiceCatalog;
import com.example.mybatis.vo.BuyerVo;
import com.example.mybatis.vo.PaymentOrderVo;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/9
 */
@Data
public class MakerTotalInvoiceInfoVo {

    /**
     * 支付信息
     */
    private List<PaymentOrderVo> paymentOrderVoList;

    /**
     * 购买方
     */
    private BuyerVo buyerVo;

    /**
     * 开票类目
     */
    private InvoiceCatalogVo invoiceCatalogVo;
}
