package com.example.merchant.vo.merchant;

import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.PaymentOrderVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/7
 */
@Data
public class QuerySubInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付信息
     */
    private List<PaymentOrderVO> paymentOrderVOS;

    /**
     * 销售方信息
     */
    private BuyerVO buyerVo;

    /**
     * 开票类目信息
     */
    private InvoiceCatalogVO invoiceCatalogVo;

    /**
     * 发票信息
     */
    private InvoiceInfoVO invoiceInfoVo;

    /**
     * 备注说明
     */
    private String remarks;
}
