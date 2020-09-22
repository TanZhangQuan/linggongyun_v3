package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.PaymentOrderDto;
import com.example.mybatis.dto.TobeinvoicedDto;
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

    //根据商户id查众包待开票数据
    ReturnJson getListCSIByID(TobeinvoicedDto tobeinvoicedDto);

    //根据支付id查询众包支付信息
    ReturnJson getPayOrderManyById(String id);

    ReturnJson getInvoiceDetailsByPayId(String id,Integer pageNo);

    ReturnJson getPaymentOrderMany(PaymentOrderDto paymentOrderDto);
    ReturnJson getPaymentOrderManyInfo(String id);
    ReturnJson saveOrUpdataPaymentOrderMany(PaymentOrderMany paymentOrderMany, List<PaymentInventory> paymentInventories);
    ReturnJson offlinePayment(String id, String manyPayment);

    ReturnJson getDayPaas(String merchantId);
    ReturnJson getWeekPaas(String merchantId);
    ReturnJson getMonthPaas(String merchantId);
    ReturnJson getYearPaas(String merchantId);
    ReturnJson confirmPaymentManyPaas(String id);
    ReturnJson getPaymentOrderManyPaas(PaymentOrderDto paymentOrderDto);
    ReturnJson getPaymentOrderManyInfoPaas(String id);
    ReturnJson saveOrUpdataPaymentOrderManyPaas(PaymentOrderMany paymentOrderMany, List<PaymentInventory> paymentInventories);
}
