package com.example.common.mybank.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 入账交易处理结果通知
 * 即时到账交易、充值使用该异步通知。当交易状态是交易结束（TRADE_FINISHED）时，才表示这笔交易成功
 */
@Data
public class PayInstantNotify extends BaseNotify {
    //商户网站唯一订单号
    private String outer_trade_no;
    //支付系统交易号
    private String inner_trade_no;
    //交易处理完成时间 yyyyMMddHHmmss
    private LocalDateTime gmt_close;
    //交易状态
    private String trade_status;
    //错误代码
    private String error_code;
    //错误信息
    private String error_msg;
    //交易金额
    private BigDecimal trade_amount;

}
