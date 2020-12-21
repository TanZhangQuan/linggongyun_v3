package com.example.mybatis.dto;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xiong.
 * @date 2020/9/8.
 * @time 11:10.
 * 添加任务信息dto
 */
@Data
@ApiModel(value = "添加修改任务")
public class TaskDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    private String id;

    /**
     * 任务名称
     */
    @NotBlank(message = "任务名字不能为空")
    private String taskName;

    /**
     * 任务编码
     */
    private String taskCode;

    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * 任务说明文字
     */
    @NotBlank(message = "任务说明文字不能为空")
    private String taskDesc;

    /**
     * 行业类型,俩个值拼接以/隔开
     */
    @NotBlank(message = "行业类型不能为空")
    private String industryType;

    /**
     * 任务最低费用
     */
    @Min(value = 0, message = "费用下限不能小于0")
    private BigDecimal taskCostMin;

    /**
     * 任务合作类型
     */
    @NotNull(message = "合作类型不能为空")
    @Min(value = 0, message = "合作类型不能小于0")
    @Max(value = 1, message = "合作类型不能大于1")
    private Integer cooperateMode;

    /**
     * 任务模式
     */
    @NotNull(message = "任务模式不能为空")
    @Min(value = 0, message = "任务模式不能小于0")
    @Max(value = 2, message = "任务模式不能大于2")
    private Integer taskMode;

    /**
     * 最大费用
     */
    private BigDecimal taskCostMax;

    /**
     * 创客所需技能
     */
    private String taskSkill;

    /**
     * 任务发布时间
     */
    private String releaseDate;

    /**
     * 任务截至时间
     */
    private String deadlineDate;

    /**
     * 任务发布时间
     */
    private String releaseTime;

    /**
     * 任务截至时间
     */
    private String deadlineTime;

    /**
     * 任务上线人数
     */
    private Integer upperLimit;

    /**
     * 创客ids,多个id以逗号隔开
     */
    private String makerIds;

    /**
     * 任务状态
     */
    private String state;

}
