package com.example.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 身份证正反面
 */
@Getter
@AllArgsConstructor
public enum IdCardSide {
    FRONT("front", "正面"),
    BACK("back", "反面");

    private String value;
    private String desc;
}
