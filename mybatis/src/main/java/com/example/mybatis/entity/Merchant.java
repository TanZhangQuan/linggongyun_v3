package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 商户信息
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
@TableName("tb_merchant")
public class Merchant extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主账号的ID(当为主账号是为0)
     */
    private String parentId;

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
     * 登录时用的手机号码
     */
    private String loginMobile;

    /**
     * 商户状态
     */
    private Integer status;

}
