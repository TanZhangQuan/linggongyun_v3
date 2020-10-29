package com.example.merchant.dto.myBank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getBank_account_no() {
        return bank_account_no;
    }

    public void setBank_account_no(String bank_account_no) {
        this.bank_account_no = bank_account_no;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getBank_line_no() {
        return bank_line_no;
    }

    public void setBank_line_no(String bank_line_no) {
        this.bank_line_no = bank_line_no;
    }

    public String getBank_branch() {
        return bank_branch;
    }

    public void setBank_branch(String bank_branch) {
        this.bank_branch = bank_branch;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getCard_attribute() {
        return card_attribute;
    }

    public void setCard_attribute(String card_attribute) {
        this.card_attribute = card_attribute;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getOuter_inst_order_no() {
        return outer_inst_order_no;
    }

    public void setOuter_inst_order_no(String outer_inst_order_no) {
        this.outer_inst_order_no = outer_inst_order_no;
    }

    public String getOuter_trade_no() {
        return outer_trade_no;
    }

    public void setOuter_trade_no(String outer_trade_no) {
        this.outer_trade_no = outer_trade_no;
    }

    public String getWhite_channel_code() {
        return white_channel_code;
    }

    public void setWhite_channel_code(String white_channel_code) {
        this.white_channel_code = white_channel_code;
    }

    @ApiModelProperty(value = "账户类型,会员提现，暂只支持BASIC", required = true, example = "BASIC")
    @NotBlank(message = "请输入账户类型")
    private String white_channel_code;
}
