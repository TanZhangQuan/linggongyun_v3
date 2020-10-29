package com.example.merchant.dto.myBank;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel("个人会员")
public class Personal extends BaseRequest {
//    @ApiModelProperty("用户UserGUID")
//    @NotBlank(message = "请传入用户UserGUID")
//    private String uid;
    @ApiModelProperty("用户手机号")
    private String mobile;
    @ApiModelProperty("用户名")
    private String member_name;
    @ApiModelProperty("真实姓名")
    private String real_name;
    @ApiModelProperty(value = "证件类型,只支持ID_CARD", example = "ID_CARD")
    private String certificate_type;
    @ApiModelProperty(value = "身份证号码", required = true)
    @NotBlank(message = "请输入用户身份证号码")
    private String certificate_no;
    @ApiModelProperty(value = "是否验证, T/F")
    private String is_verify;

//
//    public String getUid() {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getCertificate_type() {
        return certificate_type;
    }

    public void setCertificate_type(String certificate_type) {
        this.certificate_type = certificate_type;
    }

    public String getCertificate_no() {
        return certificate_no;
    }

    public void setCertificate_no(String certificate_no) {
        this.certificate_no = certificate_no;
    }

    public String getIs_verify() {
        return is_verify;
    }

    public void setIs_verify(String is_verify) {
        this.is_verify = is_verify;
    }
}
