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

    //根据商户id查众包待开票数据
    ReturnJson getListCSIByID(QueryCrowdSourcingDto queryCrowdSourcingDto,String userId);

    //根据支付id查询众包支付信息
    ReturnJson getPayOrderManyById(String id);

    ReturnJson getInvoiceDetailsByPayId(String id, Integer pageNo,Integer pageSize);

    ReturnJson getPaymentOrderMany(String merchantId,PaymentOrderMerchantDto paymentOrderMerchantDto);

    ReturnJson getPaymentOrderManyInfo(String id);

    ReturnJson saveOrUpdataPaymentOrderMany(AddPaymentOrderManyDto addPaymentOrderManyDto,String merchantId);

    ReturnJson offlinePayment(String id, String manyPayment);

    ReturnJson getDayPaas(String merchantId) throws CommonException;

    ReturnJson getWeekPaas(String merchantId) throws CommonException;

    ReturnJson getMonthPaas(String merchantId) throws CommonException;

    ReturnJson getYearPaas(String merchantId) throws CommonException;

    ReturnJson confirmPaymentManyPaas(String id);

    ReturnJson getPaymentOrderManyPaas(PaymentOrderDto paymentOrderDto,String managersId) throws CommonException;
}
