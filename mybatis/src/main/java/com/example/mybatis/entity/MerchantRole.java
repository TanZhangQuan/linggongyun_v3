package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "角色名称不能为空")
    private String roleName;

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号不能为空")
    private String mobileCode;
    /**
     * 角色的职位
     */
    @NotNull(message = "角色的职位不能为空")
    private String rolePosition;
    /**
     * 登录账户
     */
    @NotNull(message = "登录账户不能为空")
    private String loginAccount;
    /**
     * 登录密码
     */
    @NotNull(message = "登录密码不能为空")
    private String loginPassword;

    /**
     * 子账户状态
     */
    private Integer status;

    @TableField(exist = false)
    private List<Menu> list;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 用户修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

}
