package com.example.merchant.dto.merchant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "添加修改任务")
public class AddTaskDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务ID")
    private String id;

    @ApiModelProperty("任务名称")
    @NotNull(message = "任务名字不能为空")
    private String taskName;

    @ApiModelProperty(value = "任务说明文字")
    @NotNull(message = "任务说明文字不能为空")
    private String taskDesc;

    @ApiModelProperty(value = "任务说明图文")
    private String taskIllustration;

    @ApiModelProperty(value = "任务最低费用")
    @Min(value = 0, message = "费用下限不能小于0")
    private BigDecimal taskCostMin;

    @ApiModelProperty(value = "任务合作类型")
    @NotNull(message = "合作类型不能为空")
    private Integer cooperateMode;

    @ApiModelProperty(value = "任务模式")
    @NotNull(message = "任务模式不能为空")
    private Integer taskMode;

    @ApiModelProperty(value = "最大费用")
    private BigDecimal taskCostMax;

    @ApiModelProperty(value = "创客所需技能")
    private String taskSkill;

    @ApiModelProperty(value = "任务上线人数")
    @NotNull(message = "所需人数最少一人")
    private Integer upperLimit;

    @ApiModelProperty(value = "创客ids,多个id以逗号隔开")
    private String makerIds;

    @ApiModelProperty(value = "任务状态")
    private String state;

}
