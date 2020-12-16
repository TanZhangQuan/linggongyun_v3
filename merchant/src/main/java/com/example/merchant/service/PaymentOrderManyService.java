package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.mybatis.dto.QueryCrowdSourcingDto;
import com.example.merchant.dto.platform.PaymentOrderDto;
import com.example.merchant.dto.merchant.AddPaymentOrderManyDto;
import com.example.merchant.dto.merchant.PaymentOrderMerchantDto;
import com.example.merchant.exception.CommonException;
import com.example.mybatis.entity.PaymentOrderMany;

/**
 * <p>
 * 众包支付单信息
 * 服务类
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

    /**
     * 根据商户id查众包待开票数据
     * @param queryCrowdSourcingDto
     * @param userId
     * @return
     */
    ReturnJson getListCSIByID(QueryCrowdSourcingDto queryCrowdSourcingDto,String userId);

    /**
     * 根据支付id查询众包支付信息
     * @param id
     * @return
     */
    ReturnJson getPayOrderManyById(String id);

    /**
     * 根据支付id查找发票信息详情
     *
     * @param id
     * @param pageNo
     * @return
     */
    ReturnJson getInvoiceDetailsByPayId(String id, Integer pageNo,Integer pageSize);

    /**
     * 查询众包的支付订单
     *
     * @param paymentOrderMerchantDto
     * @return
     */
    ReturnJson getPaymentOrderMany(String merchantId,PaymentOrderMerchantDto paymentOrderMerchantDto);

    /**
     * 众包支付订单详情
     *
     * @param id
     * @return
     */
    ReturnJson getPaymentOrderManyInfo(String id);

    /**
     * 创建或修改众包支付订单
     *
     * @param addPaymentOrderManyDto
     * @return
     */
    ReturnJson saveOrUpdataPaymentOrderMany(AddPaymentOrderManyDto addPaymentOrderManyDto,String merchantId);

    /**
     * 众包线下支付
     *
     * @param id
     * @param manyPayment
     * @return
     */
    ReturnJson offlinePayment(String id, String manyPayment);

    /**
     * 众包今天的支付金额
     *
     * @param merchantId
     * @return
     */
    ReturnJson getDayPaas(String merchantId) throws CommonException;

    /**
     * 众包本周的支付金额
     *
     * @param merchantId
     * @return
     */
    ReturnJson getWeekPaas(String merchantId) throws CommonException;

    /**
     * 众包本月的支付金额
     *
     * @param merchantId
     * @return
     */
    ReturnJson getMonthPaas(String merchantId) throws CommonException;

    /**
     * 众包今年的支付金额
     *
     * @param merchantId
     * @return
     */
    ReturnJson getYearPaas(String merchantId) throws CommonException;

    /**
     * 众包确认收款
     *
     * @param id
     * @return
     */
    ReturnJson confirmPaymentManyPaas(String id);

    /**
     * 查询众包的支付订单
     *
     * @param paymentOrderDto
     * @return
     */
    ReturnJson getPaymentOrderManyPaas(PaymentOrderDto paymentOrderDto,String managersId) throws CommonException;
}
