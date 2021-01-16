package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatis.entity.PaymentInventory;

import java.util.List;

/**
 * <p>
 * 支付清单明细
 * 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface PaymentInventoryService extends IService<PaymentInventory> {

    /**
     * 查询总包的所有未支付成功的分包
     *
     * @param paymentOrderId
     * @return
     */
    List<PaymentInventory> queryPaymentInventoryToPayList(String paymentOrderId);

    /**
     * 根据订单号查询分包
     *
     * @param tradeNo
     * @return
     */
    PaymentInventory queryPaymentInventoryByTradeNo(String tradeNo);

    /**
     * 检验分包是否全部支付完成
     *
     * @param paymentOrderId
     * @return
     */
    boolean checkAllPaymentInventoryPaySuccess(String paymentOrderId);
}
