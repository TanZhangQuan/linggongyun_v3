package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.entity.PaymentOrderMany;

import java.util.List;

/**
 * <p>
 * 众包支付单信息
 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-08
 */
public interface PaymentOrderManyDao extends BaseMapper<PaymentOrderMany> {
    Double selectBy30Day(String merchantId);
    Double selectTotal(String merchantId);

    List<PaymentOrderMany> selectDay(String merchantId);
    List<PaymentOrderMany> selectWeek(String merchantId);
    List<PaymentOrderMany> selectMonth(String merchantId);
    List<PaymentOrderMany> selectYear(String merchantId);
}
