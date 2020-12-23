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

    @ApiModelProperty(value = "XXXXXX")
    private String managersId;

    @ApiModelProperty(value = "XXXXXX")
    private String realNmae;

    @ApiModelProperty(value = "XXXXXX")
    private String roleName;

    @ApiModelProperty(value = "XXXXXX")
    private String userName;

    @ApiModelProperty(value = "XXXXXX")
    private String loginMoblie;

    @ApiModelProperty(value = "XXXXXX")
    private List<MenuVO> list;
}
