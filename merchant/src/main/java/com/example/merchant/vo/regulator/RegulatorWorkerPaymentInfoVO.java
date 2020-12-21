package com.example.merchant.vo.regulator;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "创客详情中的商户支付列表明细")
public class RegulatorWorkerPaymentInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "支付编号")
    @ApiModelProperty(value = "总包或众包的支付订单ID")
    private String paymentOrderId;

    @Excel(name = "服务商名称")
    @ApiModelProperty(value = "服务商名称")
    private String taxName;

    @Excel(name = "商户名称")
    @ApiModelProperty(value = "商户名称")
    private String merchantName;

    @Excel(name = "创客姓名")
    @ApiModelProperty(value = "创客名称")
    private String workerName;

    @Excel(name = "合作类型")
    @ApiModelProperty(value = "合作类型")
    private String packageStatus;

    @Excel(name = "交易流水")
    @ApiModelProperty(value = "交易流水")
    private BigDecimal realMoney;

    @Excel(name = "完成时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

    @ApiModelProperty(value = "状态")
    private String invoiceStatus;
}
