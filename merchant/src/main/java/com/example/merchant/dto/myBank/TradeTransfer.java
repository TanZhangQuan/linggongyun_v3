package com.example.merchant.dto.myBank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel("转账入账")
public class TradeTransfer extends BaseRequest {
    @ApiModelProperty(value = "合作方业务平台订单号", required = true)
    private String outer_trade_no;

    @ApiModelProperty(value = "入款用户Id,金额增加方的用户ID（UID）或会员ID（内部会员ID）", required = true)
    private String fundin_uid;

    @ApiModelProperty(value = "入款账户类型", example = "BASIC", required = true)
    private String fundin_account_type;

    @ApiModelProperty(value = "用户Id,金额减少方的用户ID（UID），或会员ID（内部会员ID）", required = true)
    private String fundout_uid;

    @ApiModelProperty(value = "账户类型", example = "BASIC", required = true)
    private String fundout_account_type;

    @ApiModelProperty(value = "转账金额", required = true)
    private BigDecimal transfer_amount;

    @ApiModelProperty(value = "服务器异步通知页面路径", hidden = true, required = true)
    private String notify_url;
    public String getOuter_trade_no() {
        return outer_trade_no;
    }

    public void setOuter_trade_no(String outer_trade_no) {
        this.outer_trade_no = outer_trade_no;
    }

    public String getFundin_uid() {
        return fundin_uid;
    }

    public void setFundin_uid(String fundin_uid) {
        this.fundin_uid = fundin_uid;
    }

    public String getFundin_account_type() {
        return fundin_account_type;
    }

    public void setFundin_account_type(String fundin_account_type) {
        this.fundin_account_type = fundin_account_type;
    }

    public String getFundout_uid() {
        return fundout_uid;
    }

    public void setFundout_uid(String fundout_uid) {
        this.fundout_uid = fundout_uid;
    }

    public String getFundout_account_type() {
        return fundout_account_type;
    }

    public void setFundout_account_type(String fundout_account_type) {
        this.fundout_account_type = fundout_account_type;
    }

    public BigDecimal getTransfer_amount() {
        return transfer_amount;
    }

    public void setTransfer_amount(BigDecimal transfer_amount) {
        this.transfer_amount = transfer_amount;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
}
