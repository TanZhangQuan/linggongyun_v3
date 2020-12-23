package com.example.merchant.dto.platform;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@ApiModel(description = "添加代理商的参数")
public class RegulatorDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "监管部门ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "监管部门名称", required = true)
    @NotBlank(message = "监管部门名称不能为空")
    private String regulatorName;

    @ApiModelProperty(value = "地址", required = true)
    @NotBlank(message = "地址不能为空")
    private String address;

    @ApiModelProperty(value = "联系人", required = true)
    @NotBlank(message = "联系人不能为空")
    private String linkman;

    @ApiModelProperty(value = "联系电话", required = true)
    @NotBlank(message = "联系电话不能为空")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String linkMobile;

    @ApiModelProperty(value = "登录账号", required = true)
    @NotBlank(message = "登录账号不能为空")
    private String userName;

    @ApiModelProperty(value = "登录密码", required = true)
    private String passWord;

    @ApiModelProperty(value = "确认密码")
    private String confirmPassWord;

    @ApiModelProperty(value = "状态0启用，1停用", required = true)
    @NotNull(message = "状态不能为空")
    private Integer status;

}
