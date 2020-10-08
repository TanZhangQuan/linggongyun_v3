package com.example.mybatis.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 接收服务商信息
 */
@Data
public class TaxPO {

    @ApiModelProperty(notes = "公司与服务商的关联ID", value = "公司与服务商的关联ID")
    private String companyTaxId;

    @ApiModelProperty(notes = "是否有梯度价", value = "是否有梯度价")
    private Integer chargeStatus;

    @ApiModelProperty(notes = "服务商名称", value = "服务商名称")
    private String taxName;

    @ApiModelProperty(notes = "合作类型", value = "合作类型")
    private Integer packageStatus;

    @ApiModelProperty(notes = "一口价服务费", value = "一口价服务费")
    private BigDecimal serviceCharge;

    @ApiModelProperty(notes = "合作合同", value = "合作合同")
    private String contract;

    @ApiModelProperty(notes = "收款人", value = "收款人")
    private String payee;

    @ApiModelProperty(notes = "开户行", value = "开户行")
    private String bankName;

    @ApiModelProperty(notes = "银行卡号", value = "银行卡号")
    private String bankCode;
}
