package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "首页内容")
public class HomePageContentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "今天")
    private TodayVO todayVo;

    @ApiModelProperty(value = "本周")
    private WeekTradeVO weekTradeVO;

    @ApiModelProperty(value = "本月")
    private MonthTradeVO monthTradeVO;

    @ApiModelProperty(value = "本年")
    private YearTradeVO yearTradeVO;
}
