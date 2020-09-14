package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 业务员信息
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_sales_man")
public class SalesMan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务员ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 业务员名称
     */
    private String salesManName;

    /**
     * 业务员手机号
     */
    private String mobileCode;

    /**
     * 业务员简介
     */
    private String salesManDesc;

    /**
     * 业务员真实姓名
     */
    private String salesManRealName;

    /**
     * 业务员登录账号
     */
    private String salesManUserName;

    /**
     * 业务员登录密码
     */
    private String salesManPwd;

    /**
     * 业务员状态
     */
    private Integer salesManStatus;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;
}
