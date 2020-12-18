package com.example.common.mybank.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 转账交易处理结果通知
 * 转账使用该异步通知。当交易状态是交易结束（TRADE_FINISHED）时，才表示这笔交易成功
 */
@Data
public class TradePayAccountNotify extends BaseNotify {
    //商户网站唯一订单号
    private String outer_trade_no;
    //支付系统交易号
    private String inner_trade_no;
    //交易处理完成时间
    private LocalDateTime gmt_close;
    //交易状态  TRADE_FINISHED 转账成功  TRADE_FAILED  转账失败
    private String transfer_status;
    //错误代码
    private String error_code;
    //错误信息
    private String error_msg;

}
