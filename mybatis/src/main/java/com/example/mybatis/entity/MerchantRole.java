package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 商户角色信息
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
@TableName("tb_merchant_role")
public class MerchantRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @NotNull(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色的职位
     */
    @NotNull(message = "角色的职位不能为空")
    private String rolePosition;

    @TableField(exist = false)
    private List<Menu> list;

}
