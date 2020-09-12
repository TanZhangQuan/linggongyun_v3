package com.example.mybatis.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import java.time.LocalTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("任务信息")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务id
     */
    @ApiModelProperty("任务id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    /**
     * 商户ID(用来判断是哪个商户发布的任务)
     */
    @ApiModelProperty("商户ID")
    private String merchantId;

    /**
     * 任务的发票信息
     */
    @ApiModelProperty("任务的发票信息")
    private String invoiceId;

    /**
     * 任务编号
     */
    @ApiModelProperty("任务编号")
    private String taskCode;

    /**
     * 任务名称
     */
    @ApiModelProperty("任务名称")
    private String taskName;

    /**
     * 发布时间
     */
    @ApiModelProperty("发布时间")
    private LocalDateTime releaseDate;

    /**
     * 截止时间
     */
    @ApiModelProperty("截止时间")
    private LocalDateTime deadlineDate;


    /**
     * 任务说明
     */
    @ApiModelProperty("任务说明")
    private String taskDesc;

    /**
     * 行业id
     */
    @ApiModelProperty("行业类型")
    private String industryType;

    /**
     * 最小费用
     */
    @ApiModelProperty("最小费用")
    private BigDecimal taskCostMin;

    /**
     * 最大费用
     */
    @ApiModelProperty("最大费用")
    private BigDecimal taskCostMax;

    /**
     * 创客所需技能
     */
    @ApiModelProperty("创客所需技能")
    private String taskSkill;

    /**
     * 任务开始时间
     */
    @ApiModelProperty("任务开始时间")
    private LocalTime releaseTime;

    /**
     * 任务结束时间
     */
    @ApiModelProperty("任务结束时间")
    private LocalTime deadlineTime;

    /**
     * 任务上线人数
     */
    @ApiModelProperty("任务上线人数")
    private Integer upperLimit;

    /**
     * 合作类型
     */
    @ApiModelProperty("合作类型")
    private String cooperateMode;

    /**
     * 任务模式
     */
    @ApiModelProperty("任务模式")
    private String taskMode;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private LocalDateTime updateDate;

    /**
     * 任务状态:0发布中,1已接单,2交付中,3已完毕,4已关闭
     */
    @ApiModelProperty("任务状态")
    private String state;



}
