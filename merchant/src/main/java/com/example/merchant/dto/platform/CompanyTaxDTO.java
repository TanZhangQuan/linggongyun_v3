package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(description = "公司服务商合作信息")
public class CompanyTaxDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公司ID")
    @NotBlank(message = "公司ID不能为空")
    private String companyId;

    @ApiModelProperty(value = "服务商ID")
    @NotBlank(message = "服务商ID不能为空")
    private String taxId;

    @ApiModelProperty(value = "费用类型0为一口价，1为梯度价")
    @NotNull(message = "费用类型不能为空")
    private Integer chargeStatus;

    @ApiModelProperty(value = "一口价的服务费(如果为梯度价这为空)")
    private BigDecimal serviceCharge;

    @ApiModelProperty(value = "合作类型")
    @NotNull(message = "合作类型不能为空")
    private Integer packageStatus;

    @ApiModelProperty(value = "合作合同地址")
    @NotBlank(message = "合同地址不能为空")
    private String contract;

    @ApiModelProperty(value = "总包信息")
    @Valid
    private List<AddCompanyLadderServiceDTO> addCompanyLadderServiceDtoList;

}
