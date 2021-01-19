package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.merchant.service.PaymentHistoryService;
import com.example.mybatis.entity.PaymentHistory;
import com.example.mybatis.mapper.PaymentHistoryDao;
import org.springframework.stereotype.Service;

@Service
public class PaymentHistoryServiceImpl extends ServiceImpl<PaymentHistoryDao, PaymentHistory> implements PaymentHistoryService {

    @Override
    public PaymentHistory queryPaymentHistoryByOuterTradeNo(String outerTradeNo) {

        QueryWrapper<PaymentHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentHistory::getOuterTradeNo, outerTradeNo);

        return baseMapper.selectOne(queryWrapper);
    }
}
