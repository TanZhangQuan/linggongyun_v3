package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.enums.OrderType;
import com.example.common.enums.PaymentMethod;
import com.example.common.enums.TradeObject;
import com.example.common.enums.TradeStatus;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.PaymentHistory;

import java.util.Date;

public interface PaymentHistoryService extends IService<PaymentHistory> {

    /**
     * 根据订单号查询交易记录
     *
     * @param outerTradeNo
     * @return
     */
    PaymentHistory queryPaymentHistoryByOuterTradeNo(String outerTradeNo);

    /**
     * 查询交易记录
     *
     * @param tradeObject
     * @param tradeObjectId
     * @param beginDate
     * @param endDate
     * @param orderType
     * @param paymentMethod
     * @param tradeStatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    ReturnJson queryPaymentHistoryList(TradeObject tradeObject, String tradeObjectId, Date beginDate, Date endDate, OrderType orderType, PaymentMethod paymentMethod, TradeStatus tradeStatus, long pageNo, long pageSize);
}
