package com.example.merchant.dto.myBank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ApiModel("单笔提现")
public class TradePayToCard extends BaseRequest {
    @ApiModelProperty(value = "合作方业务平台订单号", required = true)
    @NotBlank(message = "请输入订单ID")
    private String outer_trade_no;

    @ApiModelProperty(value = "合作方对接出款渠道使用的提现订单号。若出款渠道是网商银行，则此处填写与outer_trade_no保持一致", required = true)
    @NotBlank(message = "请输入合作方对接出款渠道使用的提现订单号")
    private String outer_inst_order_no;

    @ApiModelProperty(value = "平台专属出款渠道编码，该栏位的可选列表由网商银行小二根据平台递交的申请表分配并反馈。编码规则：出款渠道编码+5位序号。如：出款渠道为网商，网商分配反馈的编码则可以是MYBANK00012，具体编码以小二反馈信息为准。", required = true)
    @NotBlank(message = "请输入平台专属出款渠道编码")
    private String white_channel_code;

    @ApiModelProperty(value = "账户类型,会员提现", example = "BASIC", required = true)
    @NotBlank(message = "请输入账户类型")
    private String account_type;

    @ApiModelProperty(value = "会员在交易见证平台绑定绑卡id，即10401绑银行卡接口或者10404绑支付宝接口返回的bank_id", required = true)
    @NotBlank(message = "请输入绑卡ID")
    private String bank_id;

    @ApiModelProperty(value = "提现金额。金额必须不大于账户可用余额", required = true)
    @NotNull(message = "请输入提现金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "服务器异步通知页面路径", hidden = true)
    private String notify_url;

    @ApiModelProperty(value = "手续费信息，json格式。buyerFee即收取会员提现手续费", example = "{\"buyerFee\":\"0.3\"}")
    private String fee_info;

    public String getOuter_trade_no() {
        return outer_trade_no;
    }

    public void setOuter_trade_no(String outer_trade_no) {
        this.outer_trade_no = outer_trade_no;
    }

    public String getOuter_inst_order_no() {
        return outer_inst_order_no;
    }

    public void setOuter_inst_order_no(String outer_inst_order_no) {
        this.outer_inst_order_no = outer_inst_order_no;
    }

    public String getWhite_channel_code() {
        return white_channel_code;
    }

    public void setWhite_channel_code(String white_channel_code) {
        this.white_channel_code = white_channel_code;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getFee_info() {
        return fee_info;
    }

    public void setFee_info(String fee_info) {
        this.fee_info = fee_info;
    }
}
