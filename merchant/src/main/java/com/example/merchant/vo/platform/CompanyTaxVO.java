package com.example.merchant.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class CompanyTaxVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公司ID")
    private String companyId;

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

    @ApiModelProperty(value = "总包信息")
    private List<CompanyLadderServiceVO> companyLadderServiceVOList;

}
