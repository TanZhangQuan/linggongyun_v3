package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.PaymentOrderSubpackage;

/**
 * <p>
 * 支付单信息
 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-08
 */
public interface PaymentOrderSubpackageService extends IService<PaymentOrderSubpackage> {
    ReturnJson subpackagePayPaas(String paymentOrderId, String subpackagePayment);
}
