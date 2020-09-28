package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 商户角色信息

 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_merchant_role")
public class MerchantRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String merchantId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色的描述
     */
    private String roleDesc;

    /**
     * 角色拥有的菜单
     */
    private String roleMenu;

    /**
     * 角色拥有的菜单
     */
    @TableField(exist = false)
    private List<Menu> menuList;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 用户修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

}
