package com.example.mybatis.vo;

import com.example.mybatis.entity.Menu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class RoleMenuPassVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "XXXXXX")
    private String managersId;

    @ApiModelProperty(value = "XXXXXX")
    private String parentId;

    @ApiModelProperty(value = "XXXXXX")
    private String realNmae;

    @ApiModelProperty(value = "XXXXXX")
    private String roleName;

    @ApiModelProperty(value = "XXXXXX")
    private String userName;

    @ApiModelProperty(value = "XXXXXX")
    private String loginMoblie;

    @ApiModelProperty(value = "XXXXXX")
    private Integer statue;

    @ApiModelProperty(value = "XXXXXX")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "XXXXXX")
    private List<Menu> list;
}
