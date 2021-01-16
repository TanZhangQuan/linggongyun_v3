package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/24
 */
@Data
@ApiModel(value = "分包支付信息")
public class RegulatorSubpackageInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "支付方式")
    private Integer paymentMode;

    @ApiModelProperty(value = "支付方")
    private String taxName;

    @ApiModelProperty(value = "开户行")
    private String bankName;

    @ApiModelProperty(value = "银行号码")
    private String bankCode;

    @ApiModelProperty(value = "收款方")
    private String paymentInventory;

    @ApiModelProperty(value = "转账金额")
    private BigDecimal workerMoney;

    @ApiModelProperty(value = "分包支付回单")
    private String subpackagePayment;
}
