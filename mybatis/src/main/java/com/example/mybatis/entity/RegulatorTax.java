package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 监管部门监管的服务商
 * </p>
 *
 * @author hzp
 * @since 2020-09-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_regulator_tax")
public class RegulatorTax implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 服务商ID
     */
    private String taxId;

    /**
     * 监管部门ID
     */
    private Long regulatorId;

    /**
     * 状态0开启监管，1关闭监管
     */
    private Integer status;

    /**
     * 开始监管时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

}
