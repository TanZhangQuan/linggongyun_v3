package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.merchant.exception.CommonException;

/**
 * @author Jwei
 */
public interface HomePageService {

    /**
     * 获取首页内容
     *
     * @param merchantId
     * @return
     */
    ReturnJson getHomePageInfo(String merchantId);

    /**
     * 平台端获取首页内容
     *
     * @param userId
     * @return
     * @throws CommonException
     */
    ReturnJson getHomePageInofpaas(String userId) throws CommonException;

    /**
     * 获取今天的交易流水
     *
     * @param merchantId
     * @return
     */
    ReturnJson getTodayById(String merchantId);

    /**
     * 获取本周的交易流水
     *
     * @param merchantId
     * @return
     */
    ReturnJson getWeekTradeById(String merchantId);

    /**
     * 获取本月的交易流水
     *
     * @param merchantId
     * @return
     */
    ReturnJson getMonthTradeById(String merchantId);

    /**
     * 获取今年的交易流水
     *
     * @param merchantId
     * @return
     */
    ReturnJson getYearTradeById(String merchantId);

    /**
     * 获取商户账户信息
     *
     * @param merchantId
     * @return
     */
    ReturnJson queryAccountInformation(String merchantId);
}
