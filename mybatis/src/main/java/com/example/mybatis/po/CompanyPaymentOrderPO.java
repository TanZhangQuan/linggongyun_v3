package com.example.mybatis.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel("公司的支付订单")
public class CompanyPaymentOrderPO {

    @ApiModelProperty("支付编号")
    private String paymentOrderId;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("服务商名称")
    private String taxName;

    @ApiModelProperty("合作类型")
    private Integer packageStatus;

    @ApiModelProperty("合同文件URL")
    private String companyContract;

    @ApiModelProperty("支付清单URL")
    private String paymentInventory;

    @ApiModelProperty("支付金额")
    private BigDecimal realMoney;

    @ApiModelProperty("是否开发票：0未开，1已开")
    private Integer isInvoice;

    @ApiModelProperty("完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime paymentDate;

}
