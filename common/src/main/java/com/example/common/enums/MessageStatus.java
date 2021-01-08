package com.example.common.enums;

import com.example.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 聊天消息状态标记
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum MessageStatus {
    UNREADE("UNREADE", "未读"),
    HASREAD("HASREAD", "已读");

    private String value;
    private String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
