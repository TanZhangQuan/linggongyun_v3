package com.example.common.enums;

import com.example.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 银联API接口
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum UnionpayMethod {
    MB010("MB010", "子账户申请开户"),
    COM02("COM02", "卡BIN查询"),
    AC021("AC021", "子账户更换绑卡"),
    AC041("AC041", "提现出款"),
    AC042("AC042", "提现结果查询"),
    AC051("AC051", "清分交易"),
    AC054("AC054", "会员间交易"),
    AC058("AC058", "内部转账交易结果查询"),
    AC081("AC081", "子账户账户余额查询"),
    AC091("AC091", "平台对账文件查询");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}