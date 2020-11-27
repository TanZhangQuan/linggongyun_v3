package com.example.common.lianlianpay.enums;

/**
 * 签名方式枚举
 *
 * @author guoyx
 * @version :1.0
 * @date:May 13, 2013 8:22:15 PM
 * 新增 sha256withRSA 2018-9-18 17:00:25
 */
public enum SignTypeEnum {

    RSA("RSA", "RSA签名"),
    SHA256withRSA("SHA256withRSA", "SHA256withRSA签名"),;
    private final String code;
    private final String msg;

    SignTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static boolean isSignType(String code) {
        for (SignTypeEnum s : SignTypeEnum.values()) {
            if (s.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }
}
