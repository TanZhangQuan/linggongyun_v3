package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * <p>
 * 任务表
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_task")
public class Task extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID(用来判断是哪个商户发布的任务)
     */
    @ApiModelProperty("商户ID")
    private String merchantId;

    /**
     * 商户ID(用来判断是哪个商户发布的任务)
     */
    @TableField(exist = false)
    @ApiModelProperty("商户名称")
    private String merchantName;

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
     * 任务说明
     */
    @ApiModelProperty("任务说明")
    private String taskDesc;

    /**
     * 行业id
     */
    @ApiModelProperty("行业类型")
    private String taskIllustration;

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
     * 任务上线人数
     */
    @ApiModelProperty("任务上线人数")
    private Integer upperLimit;

    /**
     * 合作类型
     */
    @ApiModelProperty("合作类型")
    private Integer cooperateMode;

    /**
     * 任务模式
     */
    @ApiModelProperty("任务模式")
    private Integer taskMode;

    /**
     * 任务状态:0发布中,1已接单,2交付中,3已完毕,4已关闭
     */
    @ApiModelProperty("任务状态")
    private Integer state;

}
