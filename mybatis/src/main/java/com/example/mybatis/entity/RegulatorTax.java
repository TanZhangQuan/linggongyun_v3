package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 监管部门监管的服务商
 * </p>
 *
 * @author hzp
 * @since 2020-09-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_regulator_tax")
public class RegulatorTax extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商ID
     */
    private String taxId;

    /**
     * 监管部门ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long regulatorId;

    /**
     * 状态0开启监管，1关闭监管
     */
    private Integer status;

}
