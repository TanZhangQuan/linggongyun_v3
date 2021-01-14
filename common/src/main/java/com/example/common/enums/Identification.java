package com.example.common.enums;

import com.example.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/1/13
 */
@Getter
@AllArgsConstructor
public enum Identification {

    KFDH("KFDH","客服电话"),
    YSXY("YSXY","隐私协议"),
    YHKXXXY("YHKXXXY","银行卡信息协议"),
    XCXCZZN("XCXCZZN","小程序操作指南");

    private final String value;
    private final String desc;

}
