package com.example.merchant.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(description = "添加代理商的参数")
public class AgentInfoDto {

    /**
     * 代理商ID
     */
    @Autowired
    @ApiModelProperty(notes = "代理商ID", value = "代理商ID")
    private String agentId;

    /**
     * 代理商名称
     */
    @NotBlank(message = "代理商名称不能为空！")
    @ApiModelProperty(notes = "代理商名称", value = "代理商名称", required = true)
    private String agentName;

    /**
     * 登录用户名
     */
    @NotBlank(message = "登录用户名不能为空！")
    @ApiModelProperty(notes = "登录用户名", value = "登录用户名", required = true)
    private String userName;

    /**
     * 联系人
     */
    @NotBlank(message = "联系人不能为空！")
    @ApiModelProperty(notes = "联系人", value = "联系人", required = true)
    private String linkMan;

    /**
     * 代理商电话
     */
    @NotBlank(message = "手机号不能为空！")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    @ApiModelProperty(notes = "手机号", value = "手机号", required = true)
    private String linkMobile;
    /**
     * 加盟合同
     */
    @NotBlank(message = "加盟合同不能为空！")
    @ApiModelProperty(notes = "加盟合同", value = "加盟合同", required = true)
    private String contractFile;

    /**
     * 所属业务员
     */
    @NotBlank(message = "所属业务员ID不能为空！")
    @ApiModelProperty(notes = "所属业务员ID", value = "所属业务员ID", required = true)
    private String salesManId;

    /**
     * 初始密码
     */
    @ApiModelProperty(notes = "初始密码", value = "初始密码", required = true)
    private String initPassWord;

    /**
     * 确认密码
     */
    @ApiModelProperty(notes = "确认密码", value = "确认密码", required = true)
    private String confirmPassWord;
    /**
     * 0可以用1禁用
     */
    @ApiModelProperty(notes = "状态", value = "状态", required = true)
    private Integer agentStatus = 0;

    /**
     * 1代理商
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Integer userSign = 1;

}
