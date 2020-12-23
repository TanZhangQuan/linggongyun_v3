package com.example.mybatis.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "商户的支付列表")
public class SalesManPaymentListPO {
    @ApiModelProperty(value = "总包或众包的支付订单ID")
    private String paymentOrderId;

    @ApiModelProperty(value = "商户名称")
    private String merchantName;

    @ApiModelProperty(value = "服务商名称")
    private String taxName;

    @ApiModelProperty(value = "代理商名称")
    private String agentName;

    @ApiModelProperty(value = "合作类型")
    private Integer packageStatus;

    @ApiModelProperty(value = "交易流水")
    private BigDecimal realMoney;

    @ApiModelProperty(value = "状态0未开发票，1已完成")
    private Integer isInvoice;

    @ApiModelProperty(value = "完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;
}
