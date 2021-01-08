package com.example.common.enums;

import com.example.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 身份证正反面
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum IdCardSide {
    FRONT("front", "正面"),
    BACK("back", "反面");

    private String value;
    private String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
