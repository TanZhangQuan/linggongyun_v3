package com.example.merchant.vo.merchant;

import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.PaymentOrderManyVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/7
 */
@Data
public class QueryApplicationInfoVO implements Serializable {
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
}