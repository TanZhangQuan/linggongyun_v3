package com.example.common.mybank.entity;

import java.time.LocalDateTime;

/**
 * 退款交易处理结果通知
 * 退款使用该异步通知。当交易状态是交易结束（REFUND_FINISH）时，才表示这笔交易成功
 */
public class RefundNotify extends BaseNotify {
    //商户网站唯一订单号
    private String outer_trade_no;
    //支付系统交易号
    private String inner_trade_no;
    //交易处理完成时间
    private LocalDateTime gmt_close;
    //退款状态 REFUND_FINISH 退款成功  REFUND_FAILED 退款失败
    private String trade_status;
    //错误代码
    private String error_code;
    //错误信息
    private String error_msg;

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

    public LocalDateTime getGmt_close() {
        return gmt_close;
    }

    public void setGmt_close(LocalDateTime gmt_close) {
        this.gmt_close = gmt_close;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
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
}
