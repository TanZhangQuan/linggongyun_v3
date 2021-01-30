package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 商户信息
 * </p>
 *
 * @author tzq
 * @since 2021-01-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_tax_worker")
public class TaxWorker extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主账号时为0，为子账号时是主账号的id
     */
    private String parentId;

    /**
     * 服务商ID
     */
    private String taxId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 角色名称
     */
    private String roleName;

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
     * 头像
     */
    private String headPortrait;

    /**
     * 商户状态 0正常，1停用
     */
    private Boolean status;

}
