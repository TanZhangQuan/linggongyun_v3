package com.example.merchant.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private LocalDateTime createDate;

    private LocalDateTime updateDate;


}
