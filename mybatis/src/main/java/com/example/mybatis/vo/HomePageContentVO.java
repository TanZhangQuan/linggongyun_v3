package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "首页内容")
public class HomePageContentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "XXXXXX")
    private TodayVO todayVo;

    @ApiModelProperty(value = "XXXXXX")
    private WeekTradeVO weekTradeVO;

    @ApiModelProperty(value = "XXXXXX")
    private MonthTradeVO monthTradeVO;

    @ApiModelProperty(value = "XXXXXX")
    private YearTradeVO yearTradeVO;
}
