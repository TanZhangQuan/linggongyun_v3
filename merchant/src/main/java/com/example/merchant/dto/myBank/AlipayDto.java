package com.example.merchant.dto.myBank;

import lombok.Data;

/**
 * 绑定支付宝
 */
@Data
public class AlipayDto {
    /**
     * 合作方业务平台用户ID
     */
    private String uid;

    /**
     * 支付宝账号，明文。
     */
    private String account_no;

    /**
     * 支付宝对应的真实姓名，明文。
     */
    private String account_name;

    /**
     * 身份证号
     */
    private String certificate_no;

    /**
     * 预留手机号
     */
    private String mobile_no;

    /**
     * 是否认证支付宝账号有效性
     * Y:是      N:否
     */
    private String is_verify;
}
