package com.example.common.lianlianpay.enums;

/**
 * 证件类型枚举
 * 
 * @PROJECNAME: salary-core
 * @author:yudi
 * @Date: 2019/1/22 10:50
 **/
public enum CertTypeEnum {
    IDCARD("0", "身份证");

    private final String code;
    private final String desc;

    CertTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CertTypeEnum getEnum(String value) {
        for (CertTypeEnum status : values()) {
            if (status.code.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
