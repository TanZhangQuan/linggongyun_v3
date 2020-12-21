package com.example.merchant.vo.merchant;

import com.example.mybatis.vo.BillingInfoVO;
import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.PaymentOrderVO;
import com.example.mybatis.vo.SellerVO;
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
}
