package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/2
 */
@Data
public class MenuVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 菜单的英文名称
     */
    private String menuName;

    /**
     * 菜单的中文名称
     */
    private String menuZhname;
}
