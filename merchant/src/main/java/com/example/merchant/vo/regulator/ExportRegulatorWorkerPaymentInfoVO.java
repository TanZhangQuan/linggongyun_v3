package com.example.merchant.vo.regulator;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/24
 */
@Data
@ApiModel("导出创客详情中的商户支付列表明细")
public class ExportRegulatorWorkerPaymentInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总包或众包的支付订单ID")
    @Excel(name = "支付编号")
    private String paymentOrderId;

    @ApiModelProperty(value = "服务商名称")
    @Excel(name = "服务商名称")
    private String taxName;

    @ApiModelProperty(value = "商户名称")
    @Excel(name = "商户名称")
    private String merchantName;

    @ApiModelProperty(value = "创客名称")
    @Excel(name = "创客姓名")
    private String workerName;

    @ApiModelProperty(value = "合作类型")
    @Excel(name = "合作类型")
    private String packageStatus;

    @ApiModelProperty(value = "交易流水")
    @Excel(name = "交易流水")
    private BigDecimal realMoney;

    @ApiModelProperty(value = "完成时间")
    @Excel(name = "完成时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

    @ApiModelProperty(value = "状态")
    private String invoiceStatus;
}