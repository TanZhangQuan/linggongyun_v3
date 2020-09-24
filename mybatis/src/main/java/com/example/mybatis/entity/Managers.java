package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author hzp
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_managers")
public class Managers implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 登录用户名
     */
    private String userName;

    /**
     * 部门
     */
    private String userDept;

    /**
     * 岗位
     */
    private String userPost;

    /**
     * 手机号
     */
    private String mobileCode;

    /**
     * 登录密码
     */
    private String passWord;

    /**
     * 用户的相关说明
     */
    private String userDesc;

    /**
     * 平台名称
     */
    private String paasName;

    /**
     * 0平台普通用户，1渠道商，2业务员，3服务商，4管理员
     */
    private Integer userSign;

    /**
     * 0正常，1停用
     */
    private Integer status;

    /**
     * 头像
     */
    private String userHead;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;


}
