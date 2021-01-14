package com.example.common.enums;

import com.example.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付方式
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum PaymentType {
    OFFLINE("OFFLINE", "线下支付"),
    LIANLIAN("LIANLIAN", "连连支付"),
    WANGSHANG("WANGSHANG", "网商银行支付"),
    UNIONSJBK("UNIONSJBK", "银联盛京银行支付"),
    UNIONPABK("UNIONPABK", "银联平安银行支付"),
    UNIONWSBK("UNIONWSBK", "银联网商银行支付"),
    UNIONZSBK("UNIONZSBK", "银联招商银行支付");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
