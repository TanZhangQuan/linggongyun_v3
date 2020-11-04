package com.example.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型
 */
@Getter
@AllArgsConstructor
public enum UserType {
    WORKER("WORKER", "创客"),
    MERCHANT("MERCHANT", "商户"),
    ADMIN("ADMIN", "管理员");

    private final String value;
    private final String desc;
}