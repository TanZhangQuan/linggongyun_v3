package com.example.common.enums;

import com.example.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 交易类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum TradeType {
    RECHARGE("RECHARGE", "充值"),
    WITHDRAW("WITHDRAW", "提现");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}