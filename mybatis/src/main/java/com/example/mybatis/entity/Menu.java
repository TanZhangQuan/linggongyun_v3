package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_menu")
public class Menu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单的英文名称
     */
    private String menuName;

    /**
     * 菜单的中文名称
     */
    private String menuZhname;

    /**
     * 菜单的父菜单
     */
    private String menuParent;

    /**
     * 是否为商户的权限 0是 1不是 2平台
     */
    private int isMerchant;

}
