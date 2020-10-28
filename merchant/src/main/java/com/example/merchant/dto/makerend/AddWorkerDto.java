package com.example.merchant.dto.makerend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "注册创客")
public class AddWorkerDto {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "请输入用户名")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    @NotBlank(message = "请输入密码")
    private String userPwd;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "请输入手机号")
    private String mobileCode;

    @ApiModelProperty(value = "创客性别")
    @NotNull(message = "请选择性别")
    private Integer workerSex;
}
