package com.example.common.mybank.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 单笔提现结果异步通知
 */
@Data
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
}
