package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "公司的账户信息")
public class UpdateMerchantInfDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账户信息ID")
    @NotBlank(message = "账户信息ID不能为空")
    private String id;

    @ApiModelProperty(value = "真实姓名")
    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    @ApiModelProperty(value = "登录密码")
    private String passWord;

    @ApiModelProperty(value = "支付密码")
    private String payPwd;

    @ApiModelProperty(value = "登录时用的手机号码")
    @NotBlank(message = "手机号码不能为空")
    private String loginMobile;

    @ApiModelProperty(value = "商户状态")
    private Integer status;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String userName;
}
