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
    WANGSHANG("WANGSHANG", "网商银行");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
