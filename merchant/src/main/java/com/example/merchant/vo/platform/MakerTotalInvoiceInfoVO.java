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
 * @Date 2020/12/9
 */
@Data
public class MakerTotalInvoiceInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

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
    private InvoiceCatalogVO invoiceCatalogVo;
}
