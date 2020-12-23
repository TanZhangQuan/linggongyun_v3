package com.example.merchant.dto.merchant;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description 总包支付信息
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/11/30
 */
@Data
public class PaymentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付ID")
    private String id;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "任务类型（行业类型）")
    private String taskType;

    @ApiModelProperty(value = "商户的公司简称")
    private String companySName;

    @ApiModelProperty(value = "平台服务商ID")
    @NotBlank(message = "平台服务商ID不能为空")
    private String taxId;

    @ApiModelProperty(value = "项目合同")
    @NotBlank(message = "平台服务商不能为空")
    private String companyContract;

    @ApiModelProperty(value = "支付清单")
    @NotBlank(message = "平台服务商不能为空")
    private String paymentInventory;

    @ApiModelProperty(value = "关联的任务(可以不关联)")
    private String taskId;

    @ApiModelProperty(value = "支付验收单")
    @NotBlank(message = "平台服务商不能为空")
    private String acceptanceCertificate;

    @ApiModelProperty(value = "0商户承担，1创客承担，2商户创客共同承担")
    @NotNull(message = "请选择服务费率承担者")
    private Integer taxStatus;

    @ApiModelProperty(value = "支付订单的状态")
    @ApiParam(hidden = true)
    private Integer paymentOrderStatus = 0;

    @ApiModelProperty(value = "综合税率(综合税率=商户承担的税率+创客承担的税率)")
    private BigDecimal compositeTax;

    @ApiModelProperty(value = "商户承担的税率")
    private BigDecimal merchantTax;

    @ApiModelProperty(value = "创客承担的税率")
    private BigDecimal receviceTax;
}
