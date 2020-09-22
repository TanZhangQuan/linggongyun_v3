package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.entity.PaymentOrder;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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
    BigDecimal selectBy30Day(String merchantId);
    BigDecimal selectTotal(String merchantId);

    List<PaymentOrder> selectDay(String merchantId);
    List<PaymentOrder> selectWeek(String merchantId);
    List<PaymentOrder> selectMonth(String merchantId);
    List<PaymentOrder> selectYear(String merchantId);
    List<PaymentOrder> selectMany(@Param("merchantId")String mercahntId, @Param("id") String id, @Param("taxId") String taxId, @Param("beginDate") String beginDate, @Param("endDate") String endDate,@Param("page") Integer page, @Param("pageSize")Integer pageSize);
    Integer selectManyCount(@Param("merchantId")String mercahntId, @Param("id") String id, @Param("taxId") String taxId, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    List<PaymentOrder> selectManyPaas(@Param("merchantIds")List<String> mercahntIds, @Param("merchantName")String merchantName, @Param("id") String id, @Param("taxId") String taxId, @Param("beginDate") String beginDate, @Param("endDate") String endDate,@Param("page") Integer page, @Param("pageSize")Integer pageSize);
    Integer selectManyCountPaas(@Param("merchantIds")List<String> mercahntIds, @Param("merchantName")String merchantName, @Param("id") String id, @Param("taxId") String taxId, @Param("beginDate") String beginDate, @Param("endDate") String endDate);


    BigDecimal selectBy30Daypaas(List<String> merchantId);
    BigDecimal selectTotalpaas(List<String> merchantId);

    List<PaymentOrder> selectDaypaas(List<String> merchantId);
    List<PaymentOrder> selectWeekpaas(List<String> merchantId);
    List<PaymentOrder> selectMonthpaas(List<String> merchantId);
    List<PaymentOrder> selectYearpaas(List<String> merchantId);
}
