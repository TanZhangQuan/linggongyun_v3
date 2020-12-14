package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.PaymentOrderDto;
import com.example.merchant.dto.merchant.AddPaymentOrderDto;
import com.example.merchant.dto.merchant.PaymentOrderMerchantDto;
import com.example.merchant.exception.CommonException;
import com.example.mybatis.entity.PaymentOrder;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 查询总包+分包的支付订单
     *
     * @param paymentOrderMerchantDto
     * @return
     */
    ReturnJson getPaymentOrder(String merchantId, PaymentOrderMerchantDto paymentOrderMerchantDto);

    /**
     * 查询支付订单详情
     *
     * @param id
     * @return
     */
    ReturnJson getPaymentOrderInfo(String id);

    /**
     * 插入或更新数据
     *
     * @param addPaymentOrderDto
     * @return
     */
    ReturnJson saveOrUpdataPaymentOrder(AddPaymentOrderDto addPaymentOrderDto, String merchantId);

    /**
     * 线下支付
     *
     * @param paymentOrderId
     * @param turnkeyProjectPayment
     * @return
     */
    ReturnJson offlinePayment(String paymentOrderId, String turnkeyProjectPayment);

    /**
     * 总包支付信息
     *
     * @param id
     * @return
     */
    ReturnJson getPaymentOrderById(String id);

    /**
     * 开票信息，支付
     *
     * @param id
     * @return
     */
    ReturnJson getBillingInfo(String id);

    /**
     * 获取今天的支付总额
     *
     * @param merchantId
     * @return
     */
    ReturnJson getDayPaas(String merchantId) throws CommonException;

    /**
     * 获取本周的支付总额
     *
     * @param merchantId
     * @return
     */
    ReturnJson getWeekPaas(String merchantId) throws CommonException;

    /**
     * 获取本月的支付总额
     *
     * @param merchantId
     * @return
     */
    ReturnJson getMonthPaas(String merchantId) throws CommonException;

    /**
     * 获取今年的支付总额
     *
     * @param merchantId
     * @return
     */
    ReturnJson getYearPaas(String merchantId) throws CommonException;

    /**
     * 查询总包+分包的支付订单
     *
     * @param paymentOrderDto
     * @return
     */
    ReturnJson getPaymentOrderPaas(PaymentOrderDto paymentOrderDto, String managersId) throws CommonException;

    /**
     * 线下支付
     *
     * @param paymentOrderId
     * @param turnkeyProjectPayment
     * @return
     */
    ReturnJson offlinePaymentPaas(String paymentOrderId, String turnkeyProjectPayment);

    /**
     * 确认支付
     *
     * @param paymentOrderId
     * @return
     */
    ReturnJson confirmReceiptPaas(String paymentOrderId);

    /**
     * 查询商户
     *
     * @param managersId
     * @return
     */
    ReturnJson findMerchantPaas(String managersId);

    /**
     * 平台分包支付
     *
     * @param paymentOrderId
     * @param subpackagePayment
     * @return
     */
    ReturnJson subpackagePayPaas(String paymentOrderId, String subpackagePayment);


}
