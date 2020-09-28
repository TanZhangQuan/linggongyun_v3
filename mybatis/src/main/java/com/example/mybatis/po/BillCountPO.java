package com.example.mybatis.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "账单统计")
public class BillCountPO {
    @ApiModelProperty(notes = "账单的数量",value = "月账单的数量")
    private Integer orderCount;

    @ApiModelProperty(notes = "收入统计",value = "月收入统计")
    private BigDecimal billMoney;
}
