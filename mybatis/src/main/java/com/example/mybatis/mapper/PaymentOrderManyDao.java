package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.PaymentOrderMany;
import com.example.mybatis.po.BillCountPO;
import com.example.mybatis.po.BillPO;
import com.example.mybatis.po.PaymentOrderInfoPO;
import com.example.mybatis.vo.CrowdSourcingInvoiceVo;
import com.example.mybatis.vo.InvoiceDetailsVo;
import com.example.mybatis.vo.PaymentOrderManyVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
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
    BigDecimal selectBy30Day(String merchantId);
    BigDecimal selectTotal(String merchantId);

    List<PaymentOrderMany> selectDay(String merchantId);
    List<PaymentOrderMany> selectWeek(String merchantId);
    List<PaymentOrderMany> selectMonth(String merchantId);
    List<PaymentOrderMany> selectYear(String merchantId);

    //根据商户id查众包待开票数据
    IPage<CrowdSourcingInvoiceVo> getListCSIByID(Page page,@Param("tobeinvoicedDto") TobeinvoicedDto tobeinvoicedDto);

    //根据支付id查询众包支付信息
    PaymentOrderManyVo getPayOrderManyById(String id);

    //根据众包支付id查询对应的开票清单
    IPage<InvoiceDetailsVo> getInvoiceDetailsByPayId(Page page,@Param("id") String id);

    IPage<PaymentOrderMany> selectMany(Page page, @Param("merchantId")String mercahntId, @Param("id") String id, @Param("taxId") String taxId, @Param("beginDate") String beginDate, @Param("endDate") String endDate);
    Integer selectManyCount(@Param("merchantId")String mercahntId, @Param("id") String id, @Param("taxId") String taxId, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    IPage<PaymentOrderMany> selectManyPaas(Page page,@Param("merchantIds")List<String> merchantIds, @Param("merchantName")String merchantName, @Param("id") String id, @Param("taxId") String taxId, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    BigDecimal selectBy30Daypaas(List<String> merchantId);
    BigDecimal selectTotalpaas(List<String> merchantId);

    BigDecimal selectBy30DayPaasTax(String taxId);
    BigDecimal selectTotalPaasTax(String taxId);

    BigDecimal selectBy30DayPaasRegulator(@Param("taxIds") List<String> taxIds);
    BigDecimal selectTotalPaasRegulator(@Param("taxIds")List<String> taxIds);

    List<PaymentOrderMany> selectDaypaas(List<String> merchantId);
    List<PaymentOrderMany> selectWeekpaas(List<String> merchantId);
    List<PaymentOrderMany> selectMonthpaas(List<String> merchantId);
    List<PaymentOrderMany> selectYearpaas(List<String> merchantId);


    List<BillPO> selectMonthBill(@Param("year") Integer year, @Param("month")Integer month);
    BillCountPO selectYearCount(@Param("year") Integer year);

    PaymentOrderInfoPO selectPaymentOrderInfo(String paymentOrderId);
}
