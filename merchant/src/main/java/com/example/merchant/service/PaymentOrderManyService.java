package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.PaymentOrderDto;
import com.example.merchant.dto.merchant.AddPaymentOrderManyDto;
import com.example.merchant.dto.merchant.PaymentOrderMerchantDto;
import com.example.merchant.exception.CommonException;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.PaymentOrderMany;

import javax.servlet.http.HttpServletRequest;

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
    ReturnJson getDay(HttpServletRequest request);

    ReturnJson getWeek(HttpServletRequest request);

    ReturnJson getMonth(HttpServletRequest request);

    ReturnJson getYear(HttpServletRequest request);

    //根据商户id查众包待开票数据
    ReturnJson getListCSIByID(TobeinvoicedDto tobeinvoicedDto);

    //根据支付id查询众包支付信息
    ReturnJson getPayOrderManyById(String id);

    ReturnJson getInvoiceDetailsByPayId(String id, Integer pageNo);

    ReturnJson getPaymentOrderMany(PaymentOrderMerchantDto paymentOrderMerchantDto);

    ReturnJson getPaymentOrderManyInfo(String id);

    ReturnJson saveOrUpdataPaymentOrderMany(AddPaymentOrderManyDto addPaymentOrderManyDto);

    ReturnJson offlinePayment(String id, String manyPayment);

    ReturnJson getDayPaas(HttpServletRequest request) throws CommonException;

    ReturnJson getWeekPaas(HttpServletRequest request) throws CommonException;

    ReturnJson getMonthPaas(HttpServletRequest request) throws CommonException;

    ReturnJson getYearPaas(HttpServletRequest request) throws CommonException;

    ReturnJson confirmPaymentManyPaas(String id);

    ReturnJson getPaymentOrderManyPaas(PaymentOrderDto paymentOrderDto) throws CommonException;

    ReturnJson getPaymentOrderManyInfoPaas(String id);
}
