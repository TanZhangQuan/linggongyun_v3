package com.example.merchant.vo.regulator;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel("公司的支付订单")
public class RegulatorMerchantPaymentOrderVO {

    @Excel(name = "支付编号")
    @ApiModelProperty("支付编号")
    private String paymentOrderId;

    @Excel(name = "商户")
    @ApiModelProperty("公司名称")
    private String companyName;

    @Excel(name = "平台服务商")
    @ApiModelProperty("服务商名称")
    private String taxName;

    @Excel(name = "合作类型")
    @ApiModelProperty("合作类型")
    private String packageStatus;

    @Excel(name = "项目合同")
    @ApiModelProperty("合同文件URL")
    private String companyContract;

    @Excel(name = "支付清单")
    @ApiModelProperty("支付清单URL")
    private String paymentInventory;

    @Excel(name = "交易流水")
    @ApiModelProperty("支付金额")
    private BigDecimal realMoney;

    @Excel(name = "状态")
    @ApiModelProperty("状态：0未开票，1已完成")
    private String isInvoice;

    @Excel(name = "完成时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

}
