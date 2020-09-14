package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.ReturnJson;
import com.example.merchant.dto.PaymentOrderDto;
import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.entity.PaymentOrderMany;

import java.util.List;

/**
 * <p>
 * 众包支付单信息
 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-08
 */
public interface PaymentOrderManyService extends IService<PaymentOrderMany> {
    ReturnJson getDay(String merchantId);
    ReturnJson getWeek(String merchantId);
    ReturnJson getMonth(String merchantId);
    ReturnJson getYear(String merchantId);

    ReturnJson getPaymentOrderMany(PaymentOrderDto paymentOrderDto);
    ReturnJson getPaymentOrderManyInfo(String id);
    ReturnJson saveOrUpdataPaymentOrderMany(PaymentOrderMany paymentOrderMany, List<PaymentInventory> paymentInventories);
    ReturnJson offlinePayment(String id, String manyPayment);
}
