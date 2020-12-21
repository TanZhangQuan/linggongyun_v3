package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 首页内容
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/11/16
 */
@Data
public class HomePageContentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private TodayVO todayVo;

    private WeekTradeVO weekTradeVO;

    private MonthTradeVO monthTradeVO;

    private YearTradeVO yearTradeVO;
}
