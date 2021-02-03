package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "众包支付参数")
public class PaymentOrderManyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "任务名称")
    @NotBlank(message = "任务名称不能为空")
    private String taskName;

    @ApiModelProperty(value = "平台服务商ID")
    @NotBlank(message = "平台服务商ID不能为空")
    private String taxId;

    @ApiModelProperty(value = "项目合同")
    @NotBlank(message = "项目合同（存储位置）")
    private String companyContract;

    @ApiModelProperty(value = "支付清单")
    @NotBlank(message = "支付清单（存储位置）")
    private String paymentInventory;

    @ApiModelProperty(value = "关联的任务")
    private String taskId;

    @ApiModelProperty(value = "支付验收单")
    @NotBlank(message = "支付验收单（存储位置）")
    private String acceptanceCertificate;

    @ApiModelProperty(value = "支付回单")
    @NotBlank(message = "支付回单不能为空")
    private String manyPayment;

    @ApiModelProperty(value = "服务非承担方,0商户承担，1创客承担，2共同承担")
    @ApiParam(hidden = true)
    private Integer taxStatus = 0;

}
