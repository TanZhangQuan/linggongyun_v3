package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.vo.PaymentInventoryVO;
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
    /**
     * 查询总包+分包的
     * @param paymentOrderId
     * @param workerId
     * @return
     */
    List<PaymentInventoryVO> selectPaymentInventoryList(@Param("paymentOrderId") String paymentOrderId, @Param("workerId") String workerId);

    /**
     * 查询总包+分包的
     */
    List<PaymentInventoryVO> getTotalBranchList(@Param("paymentOrderIds") String paymentOrderIds,Integer type);

    /**
     * 众包
     * @param paymentOrderId
     * @param workerId
     * @return
     */
    List<PaymentInventoryVO> selectPaymentOrderManyInfo(@Param("paymentOrderId") String paymentOrderId, @Param("workerId") String workerId);
}
