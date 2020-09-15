package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 联系人表
 * </p>
 *
 * @author hzp
 * @since 2020-09-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_linkman")
public class Linkman implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * 联系人
     */
    private String linkName;

    /**
     * 联系电话
     */
    private String linkMobile;

    /**
     * 职位
     */
    private String post;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否默认：0为默认，1为不默认
     */
    private Integer isNot;

    /**
     * 0为启用，1为停用
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
