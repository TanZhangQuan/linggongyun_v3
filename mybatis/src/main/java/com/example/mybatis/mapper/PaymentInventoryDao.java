package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.entity.PaymentInventory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 支付清单明细
 * Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface PaymentInventoryDao extends BaseMapper<PaymentInventory> {
    List<PaymentInventory> selectPaymentInventoryList(@Param("paymentOrderId") String paymentOrderId, @Param("workerId") String workerId);
}
