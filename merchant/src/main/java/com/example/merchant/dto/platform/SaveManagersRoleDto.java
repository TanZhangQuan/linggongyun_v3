package com.example.merchant.dto.platform;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SaveManagersRoleDto {

    private String managersId;

    private String roleId;

    @NotNull(message = "姓名不能为空")
    private String realName;

    @NotNull(message = "角色不能为空")
    private String roleNmae;

    @NotNull(message = "职位不能为空")
    private String rolePosition;

    private String mobileCode;

    @NotNull(message = "登录账号不能为空")
    private String userName;

    @NotNull(message = "密码不能为空")
    private String passWord;

    @NotNull(message = "没有权限不能创建该账户")
    private String menuIds;
}
