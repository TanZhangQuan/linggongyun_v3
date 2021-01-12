package com.example.mybatis.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "总包支付订单的信息")
public class PaymentOrderInfoPO {


    @ApiModelProperty("支付订单编号")
    private String paymentOrderId;

    @ApiModelProperty("商户ID")
    private String merchantId;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("服务商ID")
    private String taxId;

    @ApiModelProperty("服务商名称")
    private String platformServiceProvider;

    @ApiModelProperty("分包支付金额")
    private BigDecimal workerMoney;

    @ApiModelProperty("项目合同URL")
    private String companyContract;

    @ApiModelProperty("支付清单URL")
    private String paymentInventory;

    @ApiModelProperty("关联的任务ID(可以不关联)")
    private String taskId;

    @ApiModelProperty("支付验收单URL")
    private String acceptanceCertificate;

    @ApiModelProperty("订单状态")
    private Integer paymentOrderStatus;

    @ApiModelProperty("支付方式（总包分包支付方式相同）")
    private String paymentMode;

    @ApiModelProperty("驳回理由")
    private String reasonsForRejection;

    @ApiModelProperty("总包支付金额")
    private BigDecimal realMoney;

    @ApiModelProperty("订单创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creatDate;

    @ApiModelProperty("总包支付回单")
    private String turnkeyProjectPayment;

    @ApiModelProperty("分包支付回单")
    private String subpackagePayment;

    @ApiModelProperty("众包支付回单")
    private String manyPayment;

    @ApiModelProperty("商户名称（也是总包的支付方）")
    private String companyName;

    @ApiModelProperty(value = "开户行")
    private String bankName;

    @ApiModelProperty(value = "银行卡号")
    private String bankCode;

    @ApiModelProperty("收款人（也是分包的支付人）")
    private String payee;

    @ApiModelProperty("收款的开户行（也是分包支付的开户行）")
    private String payeeBankName;

    @ApiModelProperty("收款的银行卡号（也是分包支付的银行卡号）")
    private String payeeBankCode;

    @ApiModelProperty("创客承担的税率的百分比（如50，不需要把百分比换算成小数）")
    private BigDecimal receviceTax;

    @ApiModelProperty("商户承担的税率的百分比（如50，不需要把百分比换算成小数）")
    private BigDecimal merchantTax;

    @ApiModelProperty("0商户承担，1创客承担，2商户创客共同承担")
    private Integer taxStatus;

    @ApiModelProperty("综合税率")
    private BigDecimal compositeTax;
}
