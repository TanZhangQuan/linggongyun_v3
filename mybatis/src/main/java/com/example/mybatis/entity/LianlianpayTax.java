package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-10-27 15:46:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_lianlianpay_tax")
public class LianlianpayTax {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 服务商ID
     */
    private String taxId;

    /**
     * 连连商户号
     */
    private String oidPartner;

    /**
     * 连连商户号的私钥
     */
    private String privateKey;

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
