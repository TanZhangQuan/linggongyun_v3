package com.example.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付订单的类型
 */
@Getter
@AllArgsConstructor
public enum OrderType {
    TOTALORDER("TOTALORDER", "总包订单"),
    MANYORDER("MANYORDER", "众包订单"),
    INVENTORY("INVENTORY", "支付清单");

    private final String value;
    private final String desc;
}
