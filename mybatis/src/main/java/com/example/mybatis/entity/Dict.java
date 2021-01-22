package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author xjw
 * @since 2021-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_dict")
public class Dict extends BaseEntity {

    /**
     * 父主键
     */
    private String parentId;

    /**
     * 字典码
     */
    private String code;

    /**
     * 字典值
     */
    private String dictKey;

    /**
     * 字典名称
     */
    private String dictValue;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 字典备注
     */
    private String remark;

    /**
     * 是否已删除
     */
    private Boolean boolDeleted = true;


}
