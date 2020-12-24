package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/23
 */
@Data
@ApiModel("支付信息")
public class PayInfoVO {

    @ApiModelProperty(value = "支付方式：0线下支付")
    private String paymentMode;

    @ApiModelProperty(value = "支付方名称")
    private String companyName;

    @ApiModelProperty(value = "支付方开户行")
    private String bankName;

    @ApiModelProperty(value = "支付方银行卡号")
    private String bankCode;

    @ApiModelProperty(value = "收款方")
    private String taxName;

    @ApiModelProperty(value = "收款方开户行")
    private String tBankName;

    @ApiModelProperty(value = "收款方银行卡号")
    private String tBankCode;
}
