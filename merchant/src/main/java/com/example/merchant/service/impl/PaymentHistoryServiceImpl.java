package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.OrderType;
import com.example.common.enums.PaymentMethod;
import com.example.common.enums.TradeObject;
import com.example.common.enums.TradeStatus;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.PaymentHistoryService;
import com.example.mybatis.entity.PaymentHistory;
import com.example.mybatis.mapper.PaymentHistoryDao;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentHistoryServiceImpl extends ServiceImpl<PaymentHistoryDao, PaymentHistory> implements PaymentHistoryService {

    @Override
    public PaymentHistory queryPaymentHistory(OrderType orderType, String orderId, TradeStatus tradeStatus) {

        QueryWrapper<PaymentHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentHistory::getOrderType, orderType)
                .eq(PaymentHistory::getOrderId, orderId)
                .eq(PaymentHistory::getTradeStatus, tradeStatus);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public PaymentHistory queryPaymentHistoryByTradeNo(String tradeNo) {

        QueryWrapper<PaymentHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentHistory::getTradeNo, tradeNo);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public PaymentHistory queryPaymentHistoryByOuterTradeNo(String outerTradeNo) {

        QueryWrapper<PaymentHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentHistory::getOuterTradeNo, outerTradeNo);

        return baseMapper.selectOne(queryWrapper);

    }

    @Override
    public ReturnJson queryPaymentHistoryList(TradeObject tradeObject, String tradeObjectId, Date beginDate, Date endDate, OrderType orderType, PaymentMethod paymentMethod, TradeStatus tradeStatus, long pageNo, long pageSize) {

        if (beginDate != null && endDate != null) {
            if (beginDate.after(endDate)) {
                return ReturnJson.error("开始时间不能大于结束时间");
            }
        }

        return ReturnJson.success(baseMapper.queryPaymentHistoryList(new Page<>(pageNo, pageSize), tradeObject, tradeObjectId, beginDate, endDate, orderType, paymentMethod, tradeStatus));
    }
}
