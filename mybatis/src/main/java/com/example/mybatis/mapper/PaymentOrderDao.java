package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.entity.PaymentOrder;
import com.example.mybatis.po.BillCountPO;
import com.example.mybatis.po.BillPO;
import com.example.mybatis.po.PaymentOrderInfoPO;
import com.example.mybatis.vo.*;
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

    BigDecimal getTotalServiceMoney(String companyId);

    List<PaymentOrder> selectDay(String merchantId);
    List<PaymentOrder> selectWeek(String merchantId);
    List<PaymentOrder> selectMonth(String merchantId);
    List<PaymentOrder> selectYear(String merchantId);
    IPage<PaymentOrder> selectMany(Page page, @Param("merchantId")String mercahntId, @Param("id") String id, @Param("taxId") String taxId, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    IPage<PaymentOrder> selectManyPaas(Page page,@Param("merchantIds")List<String> mercahntIds, @Param("merchantName")String merchantName, @Param("id") String id, @Param("taxId") String taxId, @Param("beginDate") String beginDate, @Param("endDate") String endDate);


    BigDecimal selectBy30Daypaas(List<String> merchantId);
    BigDecimal selectTotalpaas(List<String> merchantId);
    BigDecimal selectTotalServiceMoney(List<String> companyId);

    BigDecimal selectBy30DayPaasTax(String taxId);
    BigDecimal selectTotalPaasTax(String taxId);
    BigDecimal selectTotalServicePaasTax(String taxId);

    BigDecimal selectBy30DayPaasRegulator(@Param("taxIds") List<String> taxIds);
    BigDecimal selectTotalPaasRegulator(@Param("taxIds")List<String> taxIds);

    List<PaymentOrder> selectDaypaas(List<String> merchantId);
    List<PaymentOrder> selectWeekpaas(List<String> merchantId);
    List<PaymentOrder> selectMonthpaas(List<String> merchantId);
    List<PaymentOrder> selectYearpaas(List<String> merchantId);

    PaymentOrderVO getPaymentOrderById(String id);

    BillingInfoVO getBillingInfo(String id);

    List<BillPO> selectMonthBill(@Param("workerId") String workerId, @Param("year") Integer year, @Param("month")Integer month,@Param("id") String id);

    BillCountPO selectYearCount(@Param("workerId") String workerId,@Param("year") Integer year);

    PaymentOrderInfoPO selectPaymentOrderInfo(String paymentOrderId);

    List<PaymentOrderVO> queryPaymentOrderInfo(String invoiceAppcationId);

    BillPO queryBillInfo(String workerId,String id);

    List<PaymentOrderVO> querySubInfo(String invoiceId);

    List<PaymentOrderVO> queryPaymentOrderInfoByIds(@Param("invoiceId")List<String> invoiceId);

    List<PaymentOrderVO> queryPaymentOrderInfoById(String invoiceId);

    BuyerVO queryBuyer(String invoiceId);

    IPage<InvoiceListVO> queryMakerPaymentInventory(Page page, String invoiceId);

    PayInfoVO queryPaymentInfo(String payId);

    QuerySubpackageInfoVO querySubpackageInfo(String payId,String workerId);

    TodayVO selectDaypaa(List<String> merchantId);

    WeekTradeVO selectWeekpaa(List<String> merchantId);

    MonthTradeVO selectMonthpaa(List<String> merchantId);

    YearTradeVO selectYearpaa(List<String> merchantId);

    BigDecimal getFlowInfo(String companyId,String taxId,Integer isNot);

    BigDecimal getInvoiceTotalMoney(String companyId,String taxId);

    BigDecimal getPaymentTotalServiceMoney(String companyId,String taxId);

    Integer getInvoiceTotalCount(String companyId,String taxId);
}
