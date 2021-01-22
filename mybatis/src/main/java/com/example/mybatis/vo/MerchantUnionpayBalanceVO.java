package com.example.mybatis.vo;

import com.example.common.enums.UnionpayBankType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "服务商银联余额详情")
public class MerchantUnionpayBalanceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "银行类型")
    private UnionpayBankType unionpayBankType;

    @ApiModelProperty(value = "服务商名称")
    private String taxName;

    @ApiModelProperty(value = "商户号")
    private String merchno;

    @ApiModelProperty(value = "平台帐户账号")
    private String acctno;

    @ApiModelProperty(value = "清分子账户")
    private String clearNo;

    @ApiModelProperty(value = "手续费子账户")
    private String serviceChargeNo;

    @ApiModelProperty(value = "子账号户名")
    private String subAccountName;

    @ApiModelProperty(value = "子账户账号")
    private String subAccountCode;

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
