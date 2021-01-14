package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.dto.QueryCrowdSourcingDTO;
import com.example.mybatis.entity.PaymentOrderMany;
import com.example.mybatis.po.BillCountPO;
import com.example.mybatis.po.BillPO;
import com.example.mybatis.po.PaymentOrderInfoPO;
import com.example.mybatis.vo.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 众包支付单信息
 * Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-08
 */
public interface PaymentOrderManyDao extends BaseMapper<PaymentOrderMany> {
    BigDecimal selectBy30Day(String merchantId);

    BigDecimal selectTotal(String merchantId);

    /**
     * 查询商户今日成交总额
     *
     * @param merchantId
     * @return
     */
    TodayVO getTodayById(String merchantId);

    /**
     * 查询商户本周成交总额
     *
     * @param merchantId
     * @return
     */
    WeekTradeVO getWeekTradeById(String merchantId);

    /**
     * 查询商户本月成交总额
     *
     * @param merchantId
     * @return
     */
    MonthTradeVO getMonthTradeById(String merchantId);

    /**
     * 查询商户本年成交总额
     *
     * @param merchantId
     * @return
     */
    YearTradeVO getYearTradeById(String merchantId);

    //根据商户id查众包待开票数据
    IPage<CrowdSourcingInvoiceInfoVO>   getListCSIByID(Page page, QueryCrowdSourcingDTO queryCrowdSourcingDto, String userId);

    //根据支付id查询众包支付信息
    PaymentOrderManyVO getPayOrderManyById(String id);

    //根据众包支付id查询对应的开票清单
    IPage<InvoiceDetailsVO> getInvoiceDetailsByPayId(Page page, @Param("id") String id);

    IPage<PaymentOrderMany> selectMany(Page page, @Param("merchantId") String mercahntId, @Param("id") String id, @Param("taxId") String taxId, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    IPage<PaymentOrderMany> selectManyPaas(Page page, @Param("merchantIds") List<String> merchantIds, @Param("merchantName") String merchantName, @Param("id") String id, @Param("taxId") String taxId, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    BigDecimal selectBy30Daypaas(List<String> merchantId);

    BigDecimal selectTotalpaas(List<String> merchantId);

    BigDecimal selectBy30DayPaasTax(String taxId);

    BigDecimal selectTotalPaasTax(String taxId);

    BigDecimal selectBy30DayPaasRegulator(@Param("taxIds") List<String> taxIds);

    BigDecimal selectTotalPaasRegulator(@Param("taxIds") List<String> taxIds);

    List<PaymentOrderMany> selectDaypaas(List<String> merchantId);

    List<PaymentOrderMany> selectWeekpaas(List<String> merchantId);

    List<PaymentOrderMany> selectMonthpaas(List<String> merchantId);

    List<PaymentOrderMany> selectYearpaas(List<String> merchantId);

    List<BillPO> selectMonthBill(@Param("workerId") String workerId, @Param("year") Integer year, @Param("month") Integer month);

    BillCountPO selectYearCount(@Param("workerId") String workerId, @Param("year") Integer year);

    BillPO queryBillInfo(String workerId, String id);

    PaymentOrderInfoPO selectPaymentOrderInfo(String paymentOrderId);

    PaymentOrderVO queryPaymentOrderVO(String payId);

    PayInfoVO queryPaymentInfo(String payId, String workerId);

    TodayVO selectDaypaa(List<String> merchantId);

    WeekTradeVO selectWeekpaa(List<String> merchantId);

    MonthTradeVO selectMonthpaa(List<String> merchantId);

    YearTradeVO selectYearpaa(List<String> merchantId);

}
