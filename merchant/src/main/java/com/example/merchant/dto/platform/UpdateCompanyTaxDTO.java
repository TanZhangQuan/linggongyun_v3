package com.example.merchant.dto.platform;

import com.example.common.enums.UnionpayBankType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(description = "服务商合作信息DTO")
public class UpdateCompanyTaxDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "合作ID")
    private String id;

    @ApiModelProperty(value = "服务商ID")
    private String taxId;

    @ApiModelProperty(value = "费用类型0为一口价，1为梯度价")
    private Integer chargeStatus;

    @ApiModelProperty(value = "一口价的服务费(如果为梯度价这为空)")
    private BigDecimal serviceCharge;

    @ApiModelProperty(value = "合作类型")
    private Integer packageStatus;

    @ApiModelProperty(value = "合作合同地址")
    private String contract;

    @ApiModelProperty(value = "开户行")
    private String bankName;

    @ApiModelProperty(value = "银行账号")
    private String bankCode;

    @ApiModelProperty(value = "银联支付银行")
    private List<UnionpayBankType> unionpayBankTypeList;

    @ApiModelProperty(value = "来款银行账号(盛京银行必传)")
    private String inBankNo;

    @ApiModelProperty(value = "总包信息")
    private List<UpdateCompanyLadderServiceDTO> updateCompanyLadderServiceDtoList;

}
