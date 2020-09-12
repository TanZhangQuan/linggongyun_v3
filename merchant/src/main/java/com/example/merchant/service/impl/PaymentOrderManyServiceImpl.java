package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.ReturnJson;
import com.example.merchant.service.PaymentOrderManyService;
import com.example.mybatis.entity.PaymentOrderMany;
import com.example.mybatis.mapper.PaymentOrderManyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 众包支付单信息
 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-08
 */
@Service
public class PaymentOrderManyServiceImpl extends ServiceImpl<PaymentOrderManyDao, PaymentOrderMany> implements PaymentOrderManyService {

    @Autowired
    private PaymentOrderManyDao paymentOrderManyDao;

    @Override
    public ReturnJson getDay(String merchantId) {
        List<PaymentOrderMany> list = paymentOrderManyDao.selectDay(merchantId);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getWeek(String merchantId) {
        List<PaymentOrderMany> list = paymentOrderManyDao.selectWeek(merchantId);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getMonth(String merchantId) {
        List<PaymentOrderMany> list = paymentOrderManyDao.selectMonth(merchantId);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getYear(String merchantId) {
        List<PaymentOrderMany> list = paymentOrderManyDao.selectYear(merchantId);
        return ReturnJson.success(list);
    }
}
