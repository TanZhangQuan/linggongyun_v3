package com.example.mybatis.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author xiong.
 * @date 2020/9/8.
 * @time 11:10.
 * 添加任务信息dto
 */
@Data
@ApiModel
public class TaskDto {

    //任务id
    @ApiModelProperty("任务id")
    private String id;

    //开票id
    @ApiModelProperty("开票id")
    private String invoiceId;

    //任务编码
    @ApiModelProperty("任务编码")
    private String taskCode;

    //任务名称
    @ApiModelProperty("任务名称")
    @NotNull(message = "任务名字不能为空")
    private String taskName;

    //商户id
    @ApiModelProperty("商户id")
    private String merchantId;

    //商户名称
    @ApiModelProperty("商户名称")
    private String merchantName;

    //任务说明文字
    @ApiModelProperty("任务说明文字")
    @NotNull(message = "任务说明文字不能为空")
    private String taskDesc;

    //行业类型,俩个值拼接以/隔开
    @ApiModelProperty("行业类型,俩个值拼接以/隔开")
    @NotNull(message = "行业类型不能为空")
    private String industryType;

    @ApiModelProperty("任务最低费用")
    @Min(value = 0,message = "费用下限不能小于0")
    private BigDecimal taskCostMin;

    //任务合作类型
    @ApiModelProperty("任务合作类型")
    @NotNull(message = "合作类型不能为空")
    @Min(value = 0,message = "合作类型不能小于0")
    @Max(value = 1,message = "合作类型不能大于1")
    private Integer cooperateMode;

    //任务模式
    @ApiModelProperty("任务模式")
    @NotNull(message = "任务模式不能为空")
    @Min(value = 0,message = "任务模式不能小于0")
    @Max(value = 2,message = "任务模式不能大于2")
    private Integer taskMode;

    //最大费用
    @ApiModelProperty("最大费用")
    private BigDecimal taskCostMax;

    //创客所需技能
    @ApiModelProperty("创客所需技能")
    private String taskSkill;

    //任务发布时间
    @ApiModelProperty("任务发布时间")
    private String releaseDate;

    //任务截至时间
    @ApiModelProperty("任务截至时间")
    private String deadlineDate;

    //任务发布时间
    @ApiModelProperty("任务发布时间")
    private String releaseTime;

    //任务截至时间
    @ApiModelProperty("任务截至时间")
    private String deadlineTime;

    //任务上线人数
    @ApiModelProperty("任务上线人数")
    private Integer upperLimit;

    //创客ids,多个id以逗号隔开
    @ApiModelProperty("创客ids,多个id以逗号隔开")
    private String makerIds;

    //任务状态
    @ApiModelProperty("任务状态")
    private String state;

}
