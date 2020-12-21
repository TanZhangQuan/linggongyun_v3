package com.example.merchant.vo.makerend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "月账单统计")
public class MonthBillCountVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "月账单的数量",value = "月账单的数量")
    private Integer monthOrderCount;

    @ApiModelProperty(notes = "月收入统计",value = "月收入统计")
    private BigDecimal monthBillMoney;
}
