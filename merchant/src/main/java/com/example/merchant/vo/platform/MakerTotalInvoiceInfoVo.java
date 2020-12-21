package com.example.merchant.vo.platform;

import com.example.merchant.vo.merchant.InvoiceCatalogVo;
import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.PaymentOrderVO;
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
    private List<PaymentOrderVO> paymentOrderVOList;

    /**
     * 购买方
     */
    private BuyerVO buyerVo;

    /**
     * 开票类目
     */
    private InvoiceCatalogVo invoiceCatalogVo;
}
