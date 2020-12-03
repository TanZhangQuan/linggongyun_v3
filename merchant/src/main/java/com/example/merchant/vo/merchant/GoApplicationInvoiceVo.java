package com.example.merchant.vo.merchant;

import com.example.mybatis.vo.BillingInfoVo;
import com.example.mybatis.vo.BuyerVo;
import com.example.mybatis.vo.PaymentOrderVo;
import com.example.mybatis.vo.SellerVo;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/2
 */
@Data
public class GoApplicationInvoiceVo {

    /**
     * 支付信息
     */
    private List<PaymentOrderVo> paymentOrderVoList;

    /**
     * 开票信息
     */
    private List<BillingInfoVo> billingInfoVoList;

    /**
     * 购买方信息
     */
    private BuyerVo buyerVo;

    /**
     * 销售方信息
     */
    private SellerVo sellerVo;
}
