package com.example.merchant.dto.merchant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "商户端总包+分包支付DTO")
public class PaymentOrderPayDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付订单ID")
    private String paymentOrderId;

    @ApiModelProperty(value = "支付方式：0线下支付,1连连支付,2网商银行支付,3银联盛京银行,4银联平安银行,5银联网商银行,6银联招商银行")
    @NotNull(message = "请选择支付方式")
    private Integer paymentMode;

    @ApiModelProperty(value = "总包支付回单")
    private String turnkeyProjectPayment;
}
