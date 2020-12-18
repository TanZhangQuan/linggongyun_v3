package com.example.common.mybank.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseNotify {
    //通知的唯一标识
    private String notifyId;
    //交易通知：trade_status_sync 支付通知：pay_status_sync 转账通知：transfer_status_sync 子账户汇入：remit_sync 单笔提现：withdrawal_status_sync
    private String notifyType;
    //通知的发送时间，格式：yyyyMMddHHmmss
    private LocalDateTime notifyTime;
    //参数字符集编码
    private String charset;
    //签名
    private String sign;
    //签名方式
    private String signType;
    //版本号
    private String version;
    //产品编码
    private String productCode;

}
