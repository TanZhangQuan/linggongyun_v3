package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class SaveManagersRoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "XXXXX")
    private String id;

    @ApiModelProperty(value = "姓名")
    @NotNull(message = "姓名不能为空")
    private String realName;

    @ApiModelProperty(value = "角色")
    @NotNull(message = "角色不能为空")
    private String roleName;

    @ApiModelProperty(value = "手机号码")
    @NotNull(message = "手机号码不能为空")
    private String mobileCode;

    @ApiModelProperty(value = "登录账号")
    @NotNull(message = "登录账号不能为空")
    private String userName;

    @ApiModelProperty(value = "密码")
    @NotNull(message = "密码不能为空")
    private String passWord;

    @ApiModelProperty(value = "权限")
    @NotNull(message = "没有权限不能创建该账户")
    private String menuIds;
}
