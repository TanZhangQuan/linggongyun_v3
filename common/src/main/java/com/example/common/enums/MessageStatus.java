package com.example.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 聊天消息状态标记
 */
@Getter
@AllArgsConstructor
public enum MessageStatus {
    UNREADE("UNREADE", "未读"),
    HASREAD("HASREAD", "已读");

    private String value;
    private String desc;
}
