package com.example.common.enums;

import com.example.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 交易状态
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum TradeStatus {
    UNTRADE("UNTRADE", "未交易"),
    TRADING("TRADING", "交易中"),
    SUCCESS("SUCCESS", "交易成功"),
    FAIL("FAIL", "交易失败");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}