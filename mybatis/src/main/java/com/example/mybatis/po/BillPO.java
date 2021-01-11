package com.example.mybatis.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "支付订单详情")
public class BillPO {

    @ApiModelProperty(notes = "支付明细ID",value = "支付明细ID")
    private String id;

    @ApiModelProperty(notes = "商户名称",value = "商户名称")
    private String merchantName;

    @ApiModelProperty(notes = "服务商名称",value = "服务商名称")
    private String taxName;

    @ApiModelProperty(notes = "项目合同",value = "项目合同")
    private String contract;

    @ApiModelProperty(notes = "支付清单",value = "支付清单")
    private String paymentInventory;

    @ApiModelProperty(notes = "任务ID",value = "任务ID")
    private String taskId;

    @ApiModelProperty(notes = "任务名称",value = "任务名称")
    private String taskName;

    @ApiModelProperty(notes = "验收单",value = "验收单")
    private String acceptanceCertificate;

    @ApiModelProperty(notes = "支付回单",value = "支付回单")
    private String paymentReceipt;

    @ApiModelProperty(notes = "支付时间",value = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

    @ApiModelProperty(notes = "金额",value = "金额")
    private BigDecimal money;
}
