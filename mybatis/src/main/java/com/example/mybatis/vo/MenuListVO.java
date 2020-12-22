package com.example.mybatis.vo;

import com.example.mybatis.entity.Menu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MenuListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 菜单英文名
     */
    private String menuName;

    /**
     * 菜单中文名
     */
    private String menuZhname;

    /**
     * 子菜单集合
     */
    private List<Menu> list;

}
