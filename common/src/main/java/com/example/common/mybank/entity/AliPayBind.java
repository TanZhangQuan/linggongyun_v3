package com.example.common.mybank.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
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
}
