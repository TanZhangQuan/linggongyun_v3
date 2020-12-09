package com.example.merchant.dto.platform;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(description = "添加代理商的参数")
public class RegulatorDto  {

    /**
     * 监管部门ID
     */
    @ApiModelProperty(notes = "监管部门ID", value = "监管部门ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 监管部门名称
     */
    @NotBlank(message = "监管部门名称不能为空！")
    @ApiModelProperty(notes = "监管部门名称", value = "监管部门名称",required = true)
    private String regulatorName;

    /**
     * 地址
     */
    @NotBlank(message = "地址不能为空！")
    @ApiModelProperty(notes = "地址", value = "地址",required = true)
    private String address;

    /**
     * 联系人
     */
    @NotBlank(message = "联系人不能为空！")
    @ApiModelProperty(notes = "联系人", value = "联系人",required = true)
    private String linkman;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空！")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    @ApiModelProperty(notes = "联系电话", value = "联系电话",required = true)
    private String linkMobile;

    /**
     * 登录账号
     */
    @NotBlank(message = "登录账号不能为空！")
    @ApiModelProperty(notes = "登录账号", value = "登录账号",required = true)
    private String userName;

    /**
     * 登录密码
     */
    @ApiModelProperty(notes = "登录密码", value = "登录密码",required = true)
    private String passWord;

    /**
     * 确认密码
     */
    @ApiModelProperty(notes = "确认密码", value = "确认密码")
    private String confirmPassWord;

    /**
     * 状态0启用，1停用
     */
    @NotNull(message = "状态不能为空！")
    @ApiModelProperty(notes = "状态0启用，1停用", value = "状态0启用，1停用",required = true)
    private Integer status;

}
