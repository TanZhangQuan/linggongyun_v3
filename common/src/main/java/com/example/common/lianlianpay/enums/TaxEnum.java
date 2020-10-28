package com.example.common.lianlianpay.enums;

/**
 * @author liuwm@lianlianpay.com
 * @description
 * @date 6/21/2019 4:21 PM
 * 
 */
public enum TaxEnum {
    // 商户站自助发薪模式
    PRE_TAX("税前"),

    // 商户站服务商模式
    AFTER_TAX("税后");

    public final String desc;

    private TaxEnum(String desc) {
        this.desc = desc;
    }
}
