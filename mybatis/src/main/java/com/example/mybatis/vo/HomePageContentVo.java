package com.example.mybatis.vo;

import lombok.Data;

/**
 * @Description 首页内容
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/11/16
 */
@Data
public class HomePageContentVo {

    private TodayVo todayVo;

    private WeekTradeVO weekTradeVO;

    private MonthTradeVO monthTradeVO;

    private YearTradeVO yearTradeVO;
}
