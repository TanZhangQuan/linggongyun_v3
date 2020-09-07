package com.example.mybatis.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import java.time.LocalTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 任务表
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_task")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 商户ID(用来判断是哪个商户发布的任务)
     */
    private String merchantId;

    /**
     * 任务的发票信息
     */
    private String invoiceId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 发布时间
     */
    private LocalDateTime releaseData;

    /**
     * 任务说明
     */
    private String taskDesc;

    /**
     * 行业id
     */
    private Integer industryType;

    /**
     * 最小费用
     */
    private BigDecimal taskCostMin;

    /**
     * 最大费用
     */
    private BigDecimal taskCostMax;

    /**
     * 创客所需技能
     */
    private String taskSkill;

    /**
     * 任务开始时间
     */
    private LocalTime taskBeginTime;

    /**
     * 任务结束时间
     */
    private LocalTime taskEndTime;

    /**
     * 任务上线人数
     */
    private Integer upperLimit;

    /**
     * 合作类型
     */
    private String cooperateMode;

    /**
     * 任务模式
     */
    private String taskMode;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    private LocalDateTime updateDate;

    /**
     * 任务状态
     */
    private String state;


}
