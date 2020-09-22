package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务商公司信息

 * </p>
 *
 * @author hzp
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_tax")
public class Tax implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 公司的简称
     */
    private String taxSName;

    /**
     * 公司的法定人
     */
    private String taxMan;

    /**
     * 公司的营业执照
     */
    private String businessLicense;

    /**
     * 公司全称
     */
    private String taxName;

    /**
     * 公司的详细地址
     */
    private String taxAddress;

    /**
     * 公司的成立时间
     */
    private LocalDateTime taxCreateDate;

    /**
     * 公司联系人
     */
    private String linkMan;

    /**
     * 公司联系电话
     */
    private String linkMobile;

    /**
     * 统一的社会信用代码
     */
    private String creditCode;

    /**
     * 公司状态0正常，1停用
     */
    private Integer taxStatus;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;


}
