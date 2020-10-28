package com.example.merchant.dto.myBank;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 添加企业会员信息
 */
@Data
public class AddEnterpriseDto {

    /**
     * uid为合作方业务平台自定义，字母或数字，不能重复，不建议使用手机号作为uid。
     * 重复提交的开户请求根据uid 作幂等返回成功。接口若调用超时，平台可重新使用相同uid重复提交
     */
    private String uid;

    /**
     * 企业名称
     */
    private String enterprise_name;

    /**
     * 企业简称
     */
    private String member_name;

    /**
     * 企业法人姓名
     */
    private String legal_person;

    /**
     * 法人证件类型
     */
    private String legal_person_certificate_type;

    /**
     * 法人证件号
     */
    private String legal_person_certificate_no;

    /**
     * 法人手机号码
     */
    private String legal_person_phone;

    /**
     * 企业网址
     */
    private String website;

    /**
     * 企业地址
     */
    private String address;

    /**
     * 执照号,若营业执照号和统一社会信用代码都填写，则取统一社会信用代码
     */
    private String license_no;

    /**
     * 营业执照所在地
     */
    private String license_address;

    /**
     * 执照过期日（营业期限）yyyymmdd
     */
    private String license_expire_date;

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
     * 统一社会信用代码
     */
    private String unified_social_credit_code;

    /**
     * 企业简介
     */
    private String summary;

    /**
     * 开户许可证
     */
    private String open_account_license;

    /**
     * 是否认证
     */
    private String is_verify;

    /**
     * 预留字段，可不填
     */
    private String login_name;

    /**
     * 预留字段，可不填
     */
    private String is_active;




}
