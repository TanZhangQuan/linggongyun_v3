package com.example.merchant.dto.myBank;

import lombok.Data;

/**
 * 修改企业会员信息
 */
@Data
public class EnterpriseDto {
    /**
     * UID 为商户ID
     */
    private String uid;

    /**
     * 企业名称
     */
    private String enterprise_name;

    /**
     * 会员名称
     */
    private String member_name;

    /**
     * 企业法人
     */
    private String legal_person;

    /**
     * 法人手机号码
     */
    private String legal_person_phone;

    /**
     * 法人证件类型  默认使用身份证
     */
    private String legal_person_certificate_type;

    /**
     * 法人证件号
     */
    private String legal_person_certificate_no;

    /**
     * 企业网址
     */
    private String website;

    /**
     * 企业地址
     */
    private String address;

    /**
     * 执照号
     */
    private String license_no;

    /**
     * 营业执照所在地
     */
    private String license_address;

    /**
     * 执照过期日（营业期限）yyyymmdd
     */
    private String licenseExpire_date;

    /**
     * 营业范围
     */
    private String business_scope;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 组织机构代码
     */
    private String organization_no;

    /**
     * 开户许可证
     */
    private String open_account_license;

    /**
     * 统一社会信用代码
     */
    private String unified_social_credit_code;

    /**
     * 企业简介
     */
    private String Summary;

    /**
     * 是否认证
     */
    private String is_verify;
}
