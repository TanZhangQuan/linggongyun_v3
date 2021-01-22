package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class QueryPassRolemenuVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "管理员ID")
    private String managersId;

    @ApiModelProperty(value = "真实名称")
    private String realNmae;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "登录电话号码")
    private String loginMoblie;

    @ApiModelProperty(value = "权限集合")
    private List<MenuVO> list;
}
