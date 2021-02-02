package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "XXXXX")
public class WorkerPayInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付ID")
    private String paymentOrderId;

    @ApiModelProperty(value = "商户名称")
    private String companySName;

    @ApiModelProperty(value = "平台服务商名称")
    private String platformServiceProvider;

    @ApiModelProperty(value = "创客名称")
    private String workerName;

    @ApiModelProperty(value = "0总包，1众包")
    private Integer packageStatus;

    @ApiModelProperty(value = "流水金额")
    private BigDecimal taskMoney;

    @ApiModelProperty(value = "支付状态")
    private Integer paymentStatus;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;
}
