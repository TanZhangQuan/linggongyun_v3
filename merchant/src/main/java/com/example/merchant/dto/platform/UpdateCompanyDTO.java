package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class UpdateCompanyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公司的基本信息")
    private UpdateCompanyInfoDTO updateCompanyInfoDto;

    @ApiModelProperty(value = "公司的开票信息")
    private UpdetaInvoiceInfoDTO updetaInvoiceInfoDto;

    @ApiModelProperty(value = "公司的账户信息")
    private UpdateMerchantInfDTO updateMerchantInfDto;

    @ApiModelProperty(value = "公司的组织结构信息")
    private UpdateCooperationDTO updateCooperationDto;

    @ApiModelProperty(value = "合作信息")
    List<UpdateCompanyTaxDTO> updateCompanyTaxDTOList;
}
