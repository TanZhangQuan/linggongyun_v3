package com.example.common.mybank.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel("单笔提现到卡")
public class WithDrawToCard extends BaseRequest {
    @ApiModelProperty(value = "账户类型,会员提现，暂只支持BASIC", required = true, example = "BASIC")
    @NotBlank(message = "请输入账户类型")
    private String account_type;

    @ApiModelProperty(value = "提现金额。金额必须不大于账户可用余额", required = true, example = "BASIC")
    @NotNull(message = "请输入提现金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "同步回调地址", hidden = true)
    private String return_url;

    @ApiModelProperty(value = "银行卡号。明文,提现到银行卡此项为银行卡号；提现到支付宝为支付宝账号", required = true)
    @NotBlank(message = "请输入银行卡号")
    private String bank_account_no;

    @ApiModelProperty(value = "户名,银行卡户名或者支付宝户名", required = true)
    @NotBlank(message = "请输入户名")
    private String account_name;

    @ApiModelProperty(value = "银行名称,提现到银行卡不可空；提现到支付宝为空")
    private String bank_name;

    @ApiModelProperty(value = "银行编号 见附录,提现到支付宝为ALIPAY", required = true)
    @NotBlank(message = "请输入银行编号")
    private String bank_code;

    @ApiModelProperty(value = "银行分支行号,提现到银行卡时，根据卡属性card_attribute 对公不可空，对私可空；提现到支付宝为空")
    private String bank_line_no;

    @ApiModelProperty(value = "支行名称,提现到银行卡时，根据卡属性card_attribute 对公不可空，对私可空；提现到支付宝为空")
    private String bank_branch;

    @ApiModelProperty(value = "卡类型:DC 借记,CC 贷记（信用卡）", required = true)
    @NotBlank(message = "请输入卡类型")
    private String card_type;

    @ApiModelProperty(value = "卡属性:C 对私,B 对公", required = true)
    @NotBlank(message = "请输入账户类型")
    private String card_attribute;

    @ApiModelProperty(value = "异步通知地址,不用填", hidden = true)
    private String notify_url;

    @ApiModelProperty(value = "外部机构订单号，合作方对接出款渠道使用的提现订单号。若出款渠道是网商银行，则此处填写与outer_trade_no保持一致", required = true)
    @NotBlank(message = "请输入外部机构订单号")
    private String outer_inst_order_no;

    @ApiModelProperty(value = "合作方业务平台订单号,合作方提交的交易号，对于合作方全局唯一", required = true)
    @NotBlank(message = "请输入订单号")
    private String outer_trade_no;

    @ApiModelProperty(value = "账户类型,会员提现，暂只支持BASIC", required = true, example = "BASIC")
    @NotBlank(message = "请输入账户类型")
    private String white_channel_code;
}
