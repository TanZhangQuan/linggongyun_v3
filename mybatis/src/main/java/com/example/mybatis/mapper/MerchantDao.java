package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.po.MerchantInfoPo;
import com.example.mybatis.po.MerchantPaymentListPO;
import com.example.mybatis.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商户信息
 * Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface MerchantDao extends BaseMapper<Merchant> {

    Merchant findByID(String id);

    List<Merchant> getIdAndName();

    String getNameById(String id);

    //购买方
    BuyerVO getBuyerById(String id);

    /**
     * 查询商户今日成交总额
     * @param merchantId
     * @return
     */
    TodayVO getTodayById(String merchantId);

    /**
     * 查询商户本周成交总额
     * @param merchantId
     * @return
     */
    WeekTradeVO getWeekTradeById(String merchantId);

    /**
     * 查询商户本月成交总额
     * @param merchantId
     * @return
     */
    MonthTradeVO getMonthTradeById(String merchantId);

    /**
     * 查询商户本年成交总额
     * @param merchantId
     * @return
     */
    YearTradeVO getYearTradeById(String merchantId);



    /*----------平台端-------------*/

    IPage<MerchantInfoPo> selectMerchantInfoPo(Page page, @Param("merchantIds") List<String> merchantIds, @Param("merchantId") String merchantId,
                                               @Param("merchantName") String merchantName, @Param("linkMobile") String linkMobile,@Param("auditStatus")Integer auditStatus);

    IPage<MerchantPaymentListPO> selectMerchantPaymentList(Page page, @Param("merchantId") String merchantId);
}
