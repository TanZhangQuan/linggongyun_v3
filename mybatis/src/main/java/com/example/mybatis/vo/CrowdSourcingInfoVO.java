package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "众包发票已开票")
public class CrowdSourcingInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发票ID")
    private String id;

    @ApiModelProperty(value = "商户ID")
    private String companyId;

    @ApiModelProperty(value = "发票code")
    private String invoiceCode;

    @ApiModelProperty(value = "商户名称")
    private String companySName;

    @ApiModelProperty(value = "服务商名称")
    private String platformServiceProvider;

    @ApiModelProperty(value = "众包支付ID")
    private String pomId;

    @ApiModelProperty(value = "支付清单")
    private String paymentInventory;

    @ApiModelProperty(value = "众包支付回单")
    private String manyPayment;

    @ApiModelProperty(value = "是否申请开票")
    private Integer isApplication;

    @ApiModelProperty(value = "发票")
    private String invoiceUrl;

    @ApiModelProperty(value = "税票")
    private String taxReceiptUrl;

    @ApiModelProperty(value = "开票状态")
    private String applicationState;

    @ApiModelProperty(value = "开票时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime invoicePrintDate;

}
