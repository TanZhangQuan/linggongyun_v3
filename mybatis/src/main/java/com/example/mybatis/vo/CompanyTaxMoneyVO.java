package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/1/8
 */
@Data
@ApiModel("服务税率")
public class CompanyTaxMoneyVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "起始金额")
    private BigDecimal startMoney;

    @ApiModelProperty(value = "结束金额")
    private BigDecimal endMoney;

    @ApiModelProperty(value = "服务税率")
    private BigDecimal serviceCharge;
}
