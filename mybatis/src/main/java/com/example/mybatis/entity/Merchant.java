package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商户信息

 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_merchant")
public class Merchant implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 公司ID（用来关联公司获取所属公司的信息）
     */
    private String companyId;

    /**
     * 公司姓名
     */
    private String companyName;

    /**
     * 角色ID（获取权限信息）
     */
    private String roleId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 支付密码
     */
    private String payPwd;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 登录密码
     */
    private String passWord;

    /**
     * 业务员ID(必填)
     */
    private String salesManId;

    /**
     * 代理商ID(可以为空)
     */
    private String agentId;

    /**
     * 登录时用的手机号码
     */
    private String loginMobile;

    /**
     * 商户状态
     */
    private Integer status;

    /**
     * 用户创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 用户修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;


}
