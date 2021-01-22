package com.example.mybatis.vo;

import com.example.mybatis.entity.Menu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class MenuVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "菜单的英文名称")
    private String menuName;

    @ApiModelProperty(value = "菜单的中文名称")
    private String menuZhname;

    @ApiModelProperty(value = "三级子菜单")
    private List<Menu> list;
}
