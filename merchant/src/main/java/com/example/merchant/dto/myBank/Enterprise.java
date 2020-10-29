package com.example.merchant.dto.myBank;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel("企业会员")
public class Enterprise extends BaseRequest {

    @ApiModelProperty("企业地址")
    private String address; 
    @ApiModelProperty("经营范围")
    private String business_scope; 
    @ApiModelProperty(value = "企业名称", required = true)
    @NotBlank(message = "请输入企业名称")
    private String enterprise_name; 
    @ApiModelProperty("法人姓名")
    private String legal_person; 
    @ApiModelProperty("法人证件号")
    private String legal_person_certificate_no; 
    @ApiModelProperty(value = "法人证件类型,只支持IC_CARD", example = "ID_CARD")
    private String legal_person_certificate_type; 
    @ApiModelProperty("法人手机号")
    private String legal_person_phone; 
    @ApiModelProperty("营业执照所在地")
    private String license_address; 
    @ApiModelProperty("执照过期日（营业期限）yyyymmdd")
    private String license_expire_date; 
    @ApiModelProperty("执照号,若营业执照号和统一社会信用代码都填写，则取统一社会信用代码")
    private String license_no; 
    @ApiModelProperty("企业简称")
    private String member_name; 
    @ApiModelProperty("开户许可证")
    private String open_account_license; 
    @ApiModelProperty("组织机构代码")
    private String organization_no; 
    @ApiModelProperty("企业简介")
    private String summary; 
    @ApiModelProperty("联系电话")
    private String telephone; 
//    @ApiModelProperty(value = "用户UserGUID", required = true)
//    @NotBlank(message = "请传入用户UserGUID")
//    private String uid;
    @ApiModelProperty("统一社会信用代码, 若做企业实名认证则不可空")
    private String unified_social_credit_code;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusiness_scope() {
        return business_scope;
    }

    public void setBusiness_scope(String business_scope) {
        this.business_scope = business_scope;
    }

    public String getEnterprise_name() {
        return enterprise_name;
    }

    public void setEnterprise_name(String enterprise_name) {
        this.enterprise_name = enterprise_name;
    }

    public String getLegal_person() {
        return legal_person;
    }

    public void setLegal_person(String legal_person) {
        this.legal_person = legal_person;
    }

    public String getLegal_person_certificate_no() {
        return legal_person_certificate_no;
    }

    public void setLegal_person_certificate_no(String legal_person_certificate_no) {
        this.legal_person_certificate_no = legal_person_certificate_no;
    }

    public String getLegal_person_certificate_type() {
        return legal_person_certificate_type;
    }

    public void setLegal_person_certificate_type(String legal_person_certificate_type) {
        this.legal_person_certificate_type = legal_person_certificate_type;
    }

    public String getLegal_person_phone() {
        return legal_person_phone;
    }

    public void setLegal_person_phone(String legal_person_phone) {
        this.legal_person_phone = legal_person_phone;
    }

    public String getLicense_address() {
        return license_address;
    }

    public void setLicense_address(String license_address) {
        this.license_address = license_address;
    }

    public String getLicense_expire_date() {
        return license_expire_date;
    }

    public void setLicense_expire_date(String license_expire_date) {
        this.license_expire_date = license_expire_date;
    }

    public String getLicense_no() {
        return license_no;
    }

    public void setLicense_no(String license_no) {
        this.license_no = license_no;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getOpen_account_license() {
        return open_account_license;
    }

    public void setOpen_account_license(String open_account_license) {
        this.open_account_license = open_account_license;
    }

    public String getOrganization_no() {
        return organization_no;
    }

    public void setOrganization_no(String organization_no) {
        this.organization_no = organization_no;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
//
//    public String getUid() {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }

    public String getUnified_social_credit_code() {
        return unified_social_credit_code;
    }

    public void setUnified_social_credit_code(String unified_social_credit_code) {
        this.unified_social_credit_code = unified_social_credit_code;
    }
}
