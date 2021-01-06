package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/31
 */
@Data
@ApiModel("未接单任务")
public class TaskWorkerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "商户名称")
    private String companyName;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "合作类型")
    private Integer cooperateMode;

    @ApiModelProperty(value = "工单模式")
    private Integer taskMode;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @ApiModelProperty(value = "任务编号")
    private String taskCode;

    @ApiModelProperty(value = "所需人数")
    private Integer upperLimit;

    @ApiModelProperty(value = "一共时间天数")
    private Integer availableTime;

    @ApiModelProperty(value = "任务最小金额")
    private BigDecimal taskCostMin;

    @ApiModelProperty(value = "任务最大金额")
    private BigDecimal taskCostMax;
}
