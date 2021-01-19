package com.example.common.enums;

import com.example.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付订单的类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum OrderType {
    RECHARGE("RECHARGE", "充值"),
    TOTALORDER("TOTALORDER", "总包服务费支付"),
    MANYORDER("MANYORDER", "众包服务费支付"),
    INVENTORY("INVENTORY", "分包支付订单支付");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
