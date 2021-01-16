package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/1/16
 */
@Data
@ApiModel(description = "支付清单信息")
public class RegulatorPayInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付清单ID")
    private String payId;

    @ApiModelProperty(value = "创客名称")
    private String workerName;

    @ApiModelProperty(value = "电话号码")
    private String mobileCode;

    @ApiModelProperty(value = "身份证号码")
    private String idCardCode;

    @ApiModelProperty(value = "银行卡号")
    private String bankCode;

    @ApiModelProperty(value = "到手金额")
    private BigDecimal realMoney;

    @ApiModelProperty(value = "发放金额")
    private BigDecimal taskMoney;

    @ApiModelProperty(value = "服务费")
    private BigDecimal serviceMoney;

    @ApiModelProperty(value = "服务费率")
    private BigDecimal compositeTax;

    @ApiModelProperty(value = "商户支出")
    private BigDecimal merchantPaymentMoney;
}
