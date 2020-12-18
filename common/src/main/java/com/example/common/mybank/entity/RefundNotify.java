package com.example.common.mybank.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 退款交易处理结果通知
 * 退款使用该异步通知。当交易状态是交易结束（REFUND_FINISH）时，才表示这笔交易成功
 */
@Data
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

}
