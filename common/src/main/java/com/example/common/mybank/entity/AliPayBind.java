package com.example.common.mybank.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel("支付宝绑定")
public class AliPayBind extends BaseRequest {
    @ApiModelProperty(value = "支付宝账号", required = true)
    @NotBlank(message = "请输入支付宝账号")
    private String account_no;

    @ApiModelProperty(value = "支付宝对应的真实姓名", required = true)
    @NotBlank(message = "请输入支付宝对应的真实姓名")
    private String account_name;

    @ApiModelProperty(value = "身份证号", required = true)
    @NotBlank(message = "请输入身份证号")
    private String certificate_no;

    @ApiModelProperty(value = "预留手机号")
    private String mobile_no;

    @ApiModelProperty(value = "是否认证支付宝账号有效性/Y:是,N:否", example = "T")
    private String is_verify;

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getCertificate_no() {
        return certificate_no;
    }

    public void setCertificate_no(String certificate_no) {
        this.certificate_no = certificate_no;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getIs_verify() {
        return is_verify;
    }

    public void setIs_verify(String is_verify) {
        this.is_verify = is_verify;
    }
}
