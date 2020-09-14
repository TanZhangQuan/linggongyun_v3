package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.entity.PaymentOrderMany;
import org.apache.ibatis.annotations.Param;

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

    List<PaymentOrderMany> selectMany(@Param("merchantId")String mercahntId, @Param("id") String id, @Param("taxId") String taxId, @Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("page") Integer page, @Param("pageSize")Integer pageSize);
    Integer selectManyCount(@Param("merchantId")String mercahntId, @Param("id") String id, @Param("taxId") String taxId, @Param("beginDate") String beginDate, @Param("endDate") String endDate);
}
