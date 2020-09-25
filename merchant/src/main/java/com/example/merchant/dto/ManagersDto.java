package com.example.merchant.dto;

import com.example.mybatis.entity.Managers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(description = "添加业务员参数")
public class ManagersDto{

    @NotBlank(message = "业务员ID！")
    @ApiModelProperty(notes = "业务员ID", value = "业务员ID",required = true)
    private String id;

    /**
     * 真实姓名
     */
    @NotBlank(message = "真实姓名不能为空！")
    @ApiModelProperty(notes = "真实姓名", value = "真实姓名",required = true)
    private String realName;

    /**
     * 登录用户名
     */
    @NotBlank(message = "登录用户名不能为空！")
    @ApiModelProperty(notes = "登录用户名", value = "登录用户名",required = true)
    private String userName;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空！")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    @ApiModelProperty(notes = "手机号", value = "手机号",required = true)
    private String mobileCode;

    /**
     * 初始密码
     */

    @ApiModelProperty(notes = "初始密码", value = "初始密码",required = true)
    private String initPassWord;

    /**
     * 确认密码
     */

    @ApiModelProperty(notes = "确认密码", value = "确认密码",required = true)
    private String confirmPassWord;
    /**
     * 2业务员
     */
    @ApiModelProperty(hidden = true)
    private Integer userSign = 2;

    /**
     * 状态0正常，1停用
     */
    @ApiModelProperty(notes = "状态", value = "状态",required = true)
    private Integer status = 0;

}
