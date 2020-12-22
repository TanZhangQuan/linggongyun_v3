package com.example.merchant.vo.makerend;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "月账单统计")
public class MonthBillCountVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 月账单的数量
     */
    private Integer monthOrderCount;

    /**
     * 月收入统计
     */
    private BigDecimal monthBillMoney;
}
