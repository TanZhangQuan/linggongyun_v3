package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.merchant.service.PaymentInventoryService;
import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.mapper.PaymentInventoryDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 支付清单明细
 * 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class PaymentInventoryServiceImpl extends ServiceImpl<PaymentInventoryDao, PaymentInventory> implements PaymentInventoryService {

    @Override
    public List<PaymentInventory> queryPaymentInventoryToPayList(String paymentOrderId) {

        QueryWrapper<PaymentInventory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentInventory::getPaymentOrderId, paymentOrderId)
                .ne(PaymentInventory::getPaymentStatus, 1);

        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public PaymentInventory queryPaymentInventoryByTradeNo(String tradeNo) {

        QueryWrapper<PaymentInventory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentInventory::getTradeNo, tradeNo);

        return baseMapper.selectOne(queryWrapper);

    }
}
