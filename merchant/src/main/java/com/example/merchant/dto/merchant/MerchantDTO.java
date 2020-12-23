package com.example.merchant.dto.merchant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "添加子用户商户信息")
public class MerchantDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "XXXXX")
    private String id;

    @ApiModelProperty(value = "XXXXX")
    @NotNull(message = "姓名不能为空")
    private String realName;

    @ApiModelProperty(value = "XXXXX")
    @NotNull(message = "角色不能为空")
    private String roleName;

    @ApiModelProperty(value = "XXXXX")
    @NotBlank(message = "手机号不能为空")
    private String mobileCode;

    @ApiModelProperty(value = "XXXXX")
    @NotNull(message = "登录账号不能为空")
    private String userName;

    @ApiModelProperty(value = "XXXXX")
    @NotNull(message = "密码不能为空")
    private String passWord;

    @ApiModelProperty(value = "XXXXX")
    @NotNull(message = "没有权限不能创建该账户")
    private String menuIds;
}
