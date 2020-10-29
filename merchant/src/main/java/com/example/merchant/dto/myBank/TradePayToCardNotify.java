package com.example.merchant.dto.myBank;

import java.time.LocalDateTime;

/**
 * 单笔提现结果异步通知
 */
public class TradePayToCardNotify extends BaseNotify {
    //商户网站提现唯一订单号 标识商户提现订单的唯一凭证号，失败后再发起需要更换
    private String outer_trade_no;
    //交易见证平台交易号
    private String inner_trade_no;
    //提现金额
    private String withdrawal_amount;
    //提现状态
    private String withdrawal_status;
    //错误代码
    private String error_code;
    //错误信息
    private String error_msg;
    //交易处理完成时间
    private LocalDateTime gmt_close;
    //用户ID
    private String uid;
    //账户类型
    private String account_type;
    //账户标识
    private String account_identity;

    public String getOuter_trade_no() {
        return outer_trade_no;
    }

    public void setOuter_trade_no(String outer_trade_no) {
        this.outer_trade_no = outer_trade_no;
    }

    public String getInner_trade_no() {
        return inner_trade_no;
    }

    public void setInner_trade_no(String inner_trade_no) {
        this.inner_trade_no = inner_trade_no;
    }

    public String getWithdrawal_amount() {
        return withdrawal_amount;
    }

    public void setWithdrawal_amount(String withdrawal_amount) {
        this.withdrawal_amount = withdrawal_amount;
    }

    public String getWithdrawal_status() {
        return withdrawal_status;
    }

    public void setWithdrawal_status(String withdrawal_status) {
        this.withdrawal_status = withdrawal_status;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public LocalDateTime getGmt_close() {
        return gmt_close;
    }

    public void setGmt_close(LocalDateTime gmt_close) {
        this.gmt_close = gmt_close;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getAccount_identity() {
        return account_identity;
    }

    public void setAccount_identity(String account_identity) {
        this.account_identity = account_identity;
    }
}
