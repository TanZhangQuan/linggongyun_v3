package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.PaymentOrderDto;
import com.example.merchant.dto.merchant.AddPaymentOrderDto;
import com.example.merchant.dto.merchant.PaymentOrderMerchantDto;
import com.example.merchant.exception.CommonException;
import com.example.mybatis.entity.PaymentOrder;

/**
 * <p>
 * 支付单信息
 * 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface PaymentOrderService extends IService<PaymentOrder> {
    ReturnJson getDay(String merchantId);

    ReturnJson getWeek(String merchantId);

    ReturnJson getMonth(String merchantId);

    ReturnJson getYear(String merchantId);

    ReturnJson getPaymentOrder(PaymentOrderMerchantDto paymentOrderMerchantDto);

    ReturnJson getPaymentOrderInfo(String id);

    ReturnJson saveOrUpdataPaymentOrder(AddPaymentOrderDto addPaymentOrderDto);

    ReturnJson offlinePayment(String paymentOrderId, String turnkeyProjectPayment);

    ReturnJson getPaymentOrderById(String id);

    ReturnJson getBillingInfo(String id);

    ReturnJson getDayPaas(String merchantId) throws CommonException;

    ReturnJson getWeekPaas(String merchantId) throws CommonException;

    ReturnJson getMonthPaas(String merchantId) throws CommonException;

    ReturnJson getYearPaas(String merchantId) throws CommonException;

    ReturnJson getPaymentOrderPaas(PaymentOrderDto paymentOrderDto) throws CommonException;

    ReturnJson getPaymentOrderInfoPaas(String id);

    ReturnJson offlinePaymentPaas(String paymentOrderId, String turnkeyProjectPayment);

    ReturnJson confirmReceiptPaas(String paymentOrderId);

    ReturnJson findMerchantPaas(String managersId);
}
