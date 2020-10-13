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

    @ApiModelProperty("服务商名称")
    private String taxName;

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

    @ApiModelProperty("总包支付金额")
    private BigDecimal realMoney;

    @ApiModelProperty("订单创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creatDate;

    @ApiModelProperty("商户名称（也是总包的支付方）")
    private String companyName;

    @ApiModelProperty("支付方的开户行")
    private String paymentBankName;

    @ApiModelProperty("支付方的银行卡号")
    private String paymentBankCode;

    @ApiModelProperty("收款人（也是分包的支付人）")
    private String payee;

    @ApiModelProperty("收款的开户行（也是分包支付的开户行）")
    private String payeeBankName;

    @ApiModelProperty("收款的银行卡号（也是分包支付的银行卡号）")
    private String payeeBankCode;
}
