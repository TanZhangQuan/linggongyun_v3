package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 监管部门
 * </p>
 *
 * @author hzp
 * @since 2020-09-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_regulator")
public class Regulator implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 监管部门ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

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
