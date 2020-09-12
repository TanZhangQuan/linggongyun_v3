package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.entity.PaymentOrder;

import java.util.List;

/**
 * <p>
 * 支付单信息
 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface PaymentOrderDao extends BaseMapper<PaymentOrder> {
    Double selectBy30Day(String merchantId);
    Double selectTotal(String merchantId);

    List<PaymentOrder> selectDay(String merchantId);
    List<PaymentOrder> selectWeek(String merchantId);
    List<PaymentOrder> selectMonth(String merchantId);
    List<PaymentOrder> selectYear(String merchantId);
}
