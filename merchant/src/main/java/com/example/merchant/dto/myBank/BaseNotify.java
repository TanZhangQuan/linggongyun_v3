package com.example.merchant.dto.myBank;

import java.time.LocalDateTime;

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

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public LocalDateTime getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(LocalDateTime notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
