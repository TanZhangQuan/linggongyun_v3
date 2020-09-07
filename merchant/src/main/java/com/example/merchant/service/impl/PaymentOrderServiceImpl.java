package com.example.merchant.service.impl;

import com.example.mybatis.entity.PaymentOrder;
import com.example.mybatis.mapper.PaymentOrderDao;
import com.example.merchant.service.PaymentOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
