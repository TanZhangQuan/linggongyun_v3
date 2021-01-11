package com.example.common.enums;

import com.example.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 银联银行类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum UnionpayBankType {
    SJBK("SJBK", "盛京银行"),
    PABK("PABK", "平安银行"),
    WSBK("WSBK", "网商银行"),
    ZSBK("ZSBK", "招商银行");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}