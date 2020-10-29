package com.example.merchant.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.AddLianLianPay;
import com.example.merchant.exception.CommonException;
import com.example.mybatis.entity.Industry;
import com.example.mybatis.entity.Lianlianpay;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface LianLianPayService extends IService<Lianlianpay> {
    /**
     * 添加连连支付商户号和私钥
     * @param merchantId
     * @param addLianLianPay
     * @return
     */
    ReturnJson addLianlianPay(String merchantId, AddLianLianPay addLianLianPay);

    /**
     * 商户支付
     * @param paymentOrderId
     * @return
     * @throws CommonException
     */
    ReturnJson merchantPay(String paymentOrderId) throws CommonException;

    /**
     * 商户支付回调
     * @param request
     */
    void merchantNotifyUrl(HttpServletRequest request);

    /**
     * 商户众包支付
     * @param paymentOrderId
     * @return
     */
    ReturnJson merchantPayMany(String paymentOrderId) throws CommonException;

    void merchantManyNotifyUrl(HttpServletRequest request);
}
