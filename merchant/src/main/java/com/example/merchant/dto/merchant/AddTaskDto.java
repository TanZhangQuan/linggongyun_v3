package com.example.merchant.dto.merchant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/1
 */
@Data
@ApiModel(value = "添加修改任务")
public class AddTaskDto {

    @ApiModelProperty(notes = "任务id",value = "任务id")
    private String id;

    @ApiModelProperty("任务名称")
    @NotNull(message = "任务名字不能为空")
    private String taskName;

    @ApiModelProperty(notes = "任务说明文字",value = "任务说明文字")
    @NotNull(message = "任务说明文字不能为空")
    private String taskDesc;

    @ApiModelProperty(notes = "行业类型,俩个值拼接以/隔开",value = "行业类型,俩个值拼接以/隔开")
    @NotNull(message = "行业类型不能为空")
    private String industryType;

    @ApiModelProperty(notes = "任务最低费用",value = "任务最低费用")
    @Min(value = 0,message = "费用下限不能小于0")
    private BigDecimal taskCostMin;

    @ApiModelProperty(notes = "任务合作类型",value = "任务合作类型")
    @NotNull(message = "合作类型不能为空")
    @Min(value = 0,message = "合作类型不能小于0")
    @Max(value = 1,message = "合作类型不能大于1")
    private Integer cooperateMode;

    @ApiModelProperty(notes = "任务模式",value = "任务模式")
    @NotNull(message = "任务模式不能为空")
    @Min(value = 0,message = "任务模式不能小于0")
    @Max(value = 2,message = "任务模式不能大于2")
    private Integer taskMode;

    @ApiModelProperty(notes = "最大费用",value = "最大费用")
    private BigDecimal taskCostMax;

    @ApiModelProperty(notes = "创客所需技能",value = "创客所需技能")
    private String taskSkill;

    @ApiModelProperty(notes = "任务发布时间,yyyy-MM-dd",value = "任务发布时间,yyyy-MM-dd")
    private String releaseDate;

    @ApiModelProperty(notes = "任务截至时间,yyyy-MM-dd",value = "任务截至时间,yyyy-MM-dd")
    private String deadlineDate;

    @ApiModelProperty(notes = "任务发布时间,HH:mm:ss",value = "任务发布时间,HH:mm:ss")
    private String releaseTime;

    @ApiModelProperty(notes = "任务截至时间,HH:mm:ss",value = "任务截至时间,HH:mm:ss")
    private String deadlineTime;

    @ApiModelProperty(notes = "任务上线人数",value = "任务上线人数")
    private Integer upperLimit;

    @ApiModelProperty(notes = "创客ids,多个id以逗号隔开",value = "创客ids,多个id以逗号隔开")
    private String makerIds;

    @ApiModelProperty(notes = "任务状态",value = "任务状态")
    private String state;

}