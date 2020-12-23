package com.example.mybatis.vo;

import com.example.mybatis.entity.Menu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class MenuListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "XXXXX")
    private String id;

    @ApiModelProperty(value = "菜单英文名")
    private String menuName;

    @ApiModelProperty(value = "菜单中文名")
    private String menuZhname;

    @ApiModelProperty(value = "子菜单集合")
    private List<Menu> list;

}
