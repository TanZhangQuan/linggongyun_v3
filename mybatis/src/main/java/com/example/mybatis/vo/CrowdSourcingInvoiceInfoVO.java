package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "众包发票待开票视图")
public class CrowdSourcingInvoiceInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "申请ID")
    private String applicationId;

    @ApiModelProperty(value = "商户ID")
    private String companyId;

    @ApiModelProperty(value = "商户名称")
    private String companySName;

    @ApiModelProperty(value = "服务商名称")
    private String platformServiceProvider;

    @ApiModelProperty(value = "众包支付ID")
    private String id;

    @ApiModelProperty(value = "众包支付清单")
    private String paymentInventory;

    @ApiModelProperty(value = "众包支付回单")
    private String manyPayment;

    @ApiModelProperty(value = "开票状态为空则为未申请")
    private String isInvoice;

    @ApiModelProperty(value = "申请状态: 0.未申请；1.申请中；2.已拒绝；3.已开票，4未开票")
    private String isApplication;

    @ApiModelProperty(value = "申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date applicationDate;

}
