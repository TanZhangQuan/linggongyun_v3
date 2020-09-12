package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.ReturnJson;
import com.example.merchant.service.PaymentOrderService;
import com.example.mybatis.entity.PaymentOrder;
import com.example.mybatis.mapper.PaymentOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 支付单信息
 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class PaymentOrderServiceImpl extends ServiceImpl<PaymentOrderDao, PaymentOrder> implements PaymentOrderService {

    @Autowired
    private PaymentOrderDao paymentOrderDao;

    @Override
    public ReturnJson getDay(String merchantId) {
        List<PaymentOrder> list = paymentOrderDao.selectDay(merchantId);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getWeek(String merchantId) {
        List<PaymentOrder> list = paymentOrderDao.selectWeek(merchantId);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getMonth(String merchantId) {
        List<PaymentOrder> list = paymentOrderDao.selectMonth(merchantId);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getYear(String merchantId) {
        List<PaymentOrder> list = paymentOrderDao.selectYear(merchantId);
        return ReturnJson.success(list);
    }
}
