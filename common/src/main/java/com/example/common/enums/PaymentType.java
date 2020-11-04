package com.example.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付方式
 */
@Getter
@AllArgsConstructor
public enum PaymentType {
    OFFLINE("OFFLINE", "线下支付"),
    LIANLIAN("LIANLIAN", "连连支付"),
    WANGSHANG("WANGSHANG", "网商银行");

    private final String value;
    private final String desc;
}
