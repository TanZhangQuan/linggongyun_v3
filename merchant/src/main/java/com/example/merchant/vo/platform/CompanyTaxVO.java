package com.example.merchant.vo.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yixi
 */
@Data
public class CompanyTaxVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "公司ID", value = "公司ID")
    private String companyId;

    @ApiModelProperty(notes = "服务商ID", value = "服务商ID")
    private String taxId;

    @ApiModelProperty(notes = "费用类型0为一口价，1为梯度价", value = "费用类型0为一口价，1为梯度价")
    private Integer chargeStatus;

    @ApiModelProperty(notes = "一口价的服务费(如果为梯度价这为空)", value = "一口价的服务费(如果为梯度价这为空)")
    private BigDecimal serviceCharge;

    @ApiModelProperty(notes = "合作类型", value = "合作类型")
    private Integer packageStatus;

    @ApiModelProperty(notes = "合作合同地址", value = "合作合同地址")
    private String contract;

    @ApiModelProperty(notes = "梯度价", value = "总包信息")
    private List<CompanyLadderServiceVO> companyLadderServiceVOList;

}
