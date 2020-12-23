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
@ApiModel("公司的支付订单")
public class RegulatorMerchantPaymentOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付编号")
    @Excel(name = "支付编号")
    private String paymentOrderId;

    @ApiModelProperty(value = "公司名称")
    @Excel(name = "商户")
    private String companyName;

    @ApiModelProperty(value = "服务商名称")
    @Excel(name = "平台服务商")
    private String taxName;

    @ApiModelProperty(value = "合作类型")
    @Excel(name = "合作类型")
    private String packageStatus;

    @ApiModelProperty(value = "合同文件URL")
    @Excel(name = "项目合同")
    private String companyContract;

    @ApiModelProperty(value = "支付清单URL")
    @Excel(name = "支付清单")
    private String paymentInventory;

    @ApiModelProperty(value = "支付金额")
    @Excel(name = "交易流水")
    private BigDecimal realMoney;

    @ApiModelProperty(value = "状态：0未开票，1已完成")
    @Excel(name = "状态")
    private String isInvoice;

    @ApiModelProperty(value = "完成时间")
    @Excel(name = "完成时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

}
