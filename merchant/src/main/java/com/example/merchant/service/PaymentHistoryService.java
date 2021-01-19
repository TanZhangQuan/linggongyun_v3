package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatis.entity.PaymentHistory;

public interface PaymentHistoryService extends IService<PaymentHistory> {

    /**
     * 根据订单号查询交易记录
     *
     * @param outerTradeNo
     * @return
     */
    PaymentHistory queryPaymentHistoryByOuterTradeNo(String outerTradeNo);

}
