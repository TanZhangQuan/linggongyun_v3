package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 监管部门
 * </p>
 *
 * @author hzp
 * @since 2020-09-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_regulator")
public class Regulator extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 监管部门名称
     */
    private String regulatorName;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 联系电话
     */
    private String linkMobile;

    /**
     * 登录账号
     */
    private String userName;

    /**
     * 登录密码
     */
    private String passWord;

    /**
     * 状态0启用，1停用
     */
    private Integer status;

}
