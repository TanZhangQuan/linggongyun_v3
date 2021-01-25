package com.example.merchant.vo.merchant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/1/25
 */
@Data
@ApiModel(description = "首页5个数据统计返还对象")
public class CompanyFlowInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "30天的众包支付总金额")
    private BigDecimal payment30ManyMoney;

    @ApiModelProperty(value = "30天的总包+分包支付总金额")
    private BigDecimal payment30TotalMoney;

    @ApiModelProperty(value = "众包支付总金额")
    private BigDecimal paymentManyMoney;

    @ApiModelProperty(value = "总包+分包支付总金额")
    private BigDecimal paymentTotalMoney;

    @ApiModelProperty(value = "众包的发票数量")
    private Integer invoiceManyCount;

    @ApiModelProperty(value = "众包的发票总金额")
    private BigDecimal invoiceManyMoney;

    @ApiModelProperty(value = "总包+分包的发票数量")
    private Integer invoiceTotalCount;

    @ApiModelProperty(value = "总包+分包的发票总金额")
    private BigDecimal invoiceTotalMoney;

}