package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 行业表
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_industry")
public class Industry extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 行业类型
     */
    private String industryType;

    /**
     * 一级id
     */
    private String oneLevel;

}
