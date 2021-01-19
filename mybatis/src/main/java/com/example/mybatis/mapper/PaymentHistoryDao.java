package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.enums.OrderType;
import com.example.common.enums.PaymentMethod;
import com.example.common.enums.TradeObject;
import com.example.common.enums.TradeStatus;
import com.example.mybatis.entity.PaymentHistory;
import com.example.mybatis.vo.PaymentHistoryListVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * <p>
 * 第三方支付记录 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-11-04
 */
public interface PaymentHistoryDao extends BaseMapper<PaymentHistory> {

    /**
     * 查询交易记录
     *
     * @param page
     * @param tradeObject
     * @param tradeObjectId
     * @param beginDate
     * @param endDate
     * @param orderType
     * @param paymentMethod
     * @param tradeStatus
     * @return
     */
    IPage<PaymentHistoryListVO> queryPaymentHistoryList(Page page, @Param("tradeObject") TradeObject tradeObject, @Param("tradeObjectId") String tradeObjectId, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate, @Param("orderType") OrderType orderType, @Param("paymentMethod") PaymentMethod paymentMethod, @Param("tradeStatus") TradeStatus tradeStatus);
}
