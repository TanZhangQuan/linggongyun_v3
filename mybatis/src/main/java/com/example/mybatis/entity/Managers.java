package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author hzp
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_managers")
public class Managers extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 父级ID
     */
    private String parentId;

    /**
     * 角色ID
     */
    private String roleName;

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

}
