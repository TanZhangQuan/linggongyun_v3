package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "服务商银联余额详情")
public class TaxUnionpayBalanceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = " 可用余额，单位元")
    private BigDecimal useBal = BigDecimal.ZERO;

    @ApiModelProperty(value = "冻结余额（平台），单位元")
    private BigDecimal pfrzBal = BigDecimal.ZERO;

    @ApiModelProperty(value = "冻结余额（银行），单位元")
    private BigDecimal bfrzBal = BigDecimal.ZERO;

    @ApiModelProperty(value = "在途余额（入），单位元")
    private BigDecimal iwayBal = BigDecimal.ZERO;

    @ApiModelProperty(value = "在途余额（出），单位元")
    private BigDecimal owayBal = BigDecimal.ZERO;

    @ApiModelProperty(value = "账面余额，单位元")
    private BigDecimal actBal = BigDecimal.ZERO;

}
