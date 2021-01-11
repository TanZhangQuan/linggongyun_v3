package com.example.common.enums;

import com.example.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum UserType {
    WORKER("WORKER", "创客"),
    MERCHANT("MERCHANT", "商户"),
    ADMIN("ADMIN", "管理员");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}