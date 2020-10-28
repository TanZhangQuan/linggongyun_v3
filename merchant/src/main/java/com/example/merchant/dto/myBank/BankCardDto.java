package com.example.merchant.dto.myBank;

import lombok.Data;

/**
 * 绑卡
 */
@Data
public class BankCardDto {

    /**
     * 合作方业务平台用户ID  不可空
     */
    private String uid;

    /**
     * 银行全称
     */
    private String bank_name;

    /**
     * 支行名称
     */
    private String bank_branch;

    /**
     * 联行号对私卡可空，对公卡必填。对公卡需传正确分支行号
     */
    private String branch_no;

    /**
     * 银行账号/卡号，明文。 不可空
     */
    private String bank_account_no;

    /**
     * 银行开户名，明文。  不可空
     */
    private String account_name;

    /**
     *卡类型  不可空
     *
     * DC   借记
     */
    private String card_type;

    /**
     * 卡属性：  不可空
     *
     * C    对私
     *
     * B    对公
     */
    private String card_attribute;

    /**
     * 验证类型
     */
    private String verify_type;

    /**
     * 验证类型
     */
    private String certificate_type;

    /**
     * 证件号
     */
    private String certificate_no;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 银行预留手机号
     */
    private String reserved_mobile;

    /**
     * 支付属性：
     *
     * NORMAL 普通(默认)
     */
    private String pay_attribute;
}
