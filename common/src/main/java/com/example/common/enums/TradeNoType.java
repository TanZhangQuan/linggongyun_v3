package com.example.common.enums;

import com.example.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 交易类型（用于订单号开头）
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum TradeNoType {
    PO("PO", "总包交易"),
    PI("PI", "分包交易"),
    POM("POM", "众包交易");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}