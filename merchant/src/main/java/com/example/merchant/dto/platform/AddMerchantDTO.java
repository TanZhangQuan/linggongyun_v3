package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "商户子账号添加")
public class AddMerchantDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty(value = "支付密码")
    @NotBlank(message = "支付密码不能为空")
    private String payPwd;

    @ApiModelProperty(value = "真实姓名")
    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    @ApiModelProperty(value = "登录密码")
    @NotBlank(message = "登录密码不能为空")
    private String passWord;

    @ApiModelProperty(value = "登录时用的手机号码")
    @NotBlank(message = "手机号码不能为空")
    private String loginMobile;
}
