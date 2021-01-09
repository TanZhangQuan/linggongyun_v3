package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.AssociatedTasksDTO;
import com.example.merchant.dto.merchant.AddPaymentOrderDTO;
import com.example.merchant.dto.merchant.PaymentOrderMerchantDTO;
import com.example.merchant.dto.platform.PaymentOrderDTO;
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

    /**
     * 查询总包+分包的支付订单
     *
     * @param paymentOrderMerchantDto
     * @return
     */
    ReturnJson getPaymentOrder(String merchantId, PaymentOrderMerchantDTO paymentOrderMerchantDto);

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
    ReturnJson saveOrUpdataPaymentOrder(AddPaymentOrderDTO addPaymentOrderDto, String merchantId);

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
    ReturnJson getPaymentOrderPaas(PaymentOrderDTO paymentOrderDto, String managersId) throws CommonException;

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

    /**
     * 获取今天的支付总额
     *
     * @param merchantId
     * @return
     * @throws CommonException
     */
    ReturnJson getDaypaa(String merchantId) throws CommonException;


    /**
     * 获取本周的支付总额
     *
     * @param merchantId
     * @return
     */
    ReturnJson getWeekPaa(String merchantId) throws CommonException;

    /**
     * 获取本月的支付总额
     *
     * @param merchantId
     * @return
     */
    ReturnJson getMonthPaa(String merchantId) throws CommonException;

    /**
     * 获取今年的支付总额
     *
     * @param merchantId
     * @return
     */
    ReturnJson getYearPaa(String merchantId) throws CommonException;

    /**
     * 获取可关联的任务
     *
     * @param merchantId
     * @param associatedTasksDTO
     * @return
     */
    ReturnJson associatedTasks(String merchantId, AssociatedTasksDTO associatedTasksDTO);

    /**
     * 驳回支付信息
     *
     * @param paymentOrderId
     * @param reasonsForRejection
     * @return
     */
    ReturnJson reject(String paymentOrderId,String reasonsForRejection);

    /**
     * 获取梯度价
     *
     * @param merchantId
     * @param taxId
     * @param packageStatus
     * @return
     */
    ReturnJson gradientPrice(String merchantId,String taxId,Integer packageStatus);
}
