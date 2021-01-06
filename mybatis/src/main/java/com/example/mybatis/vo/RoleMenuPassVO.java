package com.example.mybatis.vo;

import com.example.mybatis.entity.Menu;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "平台权限用户集合")
public class RoleMenuPassVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String managersId;

    @ApiModelProperty(value = "父级ID")
    private String parentId;

    @ApiModelProperty(value = "真实姓名")
    private String realNmae;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "电话")
    private String loginMoblie;

    @ApiModelProperty(value = "状态")
    private Integer statue;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "权限集合")
    private List<Menu> list;
}
