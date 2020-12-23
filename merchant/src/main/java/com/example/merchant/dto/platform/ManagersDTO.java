package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@ApiModel(description = "添加业务员参数")
public class ManagersDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务员ID", required = true)
    private String id;

    @ApiModelProperty(value = "真实姓名", required = true)
    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    @ApiModelProperty(value = "登录用户名", required = true)
    @NotBlank(message = "登录用户名不能为空！")
    private String userName;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号不能为空！")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String mobileCode;

    @ApiModelProperty(value = "初始密码", required = true)
    private String initPassWord;

    @ApiModelProperty(value = "2业务员", hidden = true)
    private Integer userSign = 2;

    @ApiModelProperty(value = "状态: 0正常，1停用", required = true)
    private Integer status = 0;

}
