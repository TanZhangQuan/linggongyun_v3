package com.example.merchant.vo.regulator;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "创客详情中的商户支付列表明细")
public class RegulatorWorkerPaymentInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付ID
     */
    @Excel(name = "支付编号")
    private String paymentOrderId;

    /**
     * 服务商名称
     */
    @Excel(name = "服务商名称")
    private String taxName;

    /**
     * 商户名称
     */
    @Excel(name = "商户名称")
    private String merchantName;

    /**
     * 创客姓名
     */
    @Excel(name = "创客姓名")
    private String workerName;

    /**
     * 合作类型
     */
    @Excel(name = "合作类型")
    private String packageStatus;

    /**
     * 交易流水
     */
    @Excel(name = "交易流水")
    private BigDecimal realMoney;

    /**
     * 完成时间
     */
    @Excel(name = "完成时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

    /**
     * 状态
     */
    private String invoiceStatus;
}
