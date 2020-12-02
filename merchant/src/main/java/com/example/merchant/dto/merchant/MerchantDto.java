package com.example.merchant.dto.merchant;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加子用户商户信息
 */
@Data
public class MerchantDto {

    private String id;

    @NotNull(message = "姓名不能为空")
    private String realName;

    @NotNull(message = "角色不能为空")
    private String roleNmae;

    @NotBlank(message = "手机号不能为空")
    private String mobileCode;

    @NotNull(message = "登录账号不能为空")
    private String userName;

    @NotNull(message = "密码不能为空")
    private String passWord;

    @NotNull(message = "没有权限不能创建该账户")
    private String menuIds;
}
