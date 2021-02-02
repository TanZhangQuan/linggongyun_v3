package com.example.common.enums;

import com.example.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 合作类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum PackageType {
    TOTALPACKAGE("TOTALPACKAGE", "总包"),
    CROWDPACKAGE("CROWDPACKAGE", "众包");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
