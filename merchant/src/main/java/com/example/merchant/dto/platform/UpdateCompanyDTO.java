package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "修改公司信息")
public class UpdateCompanyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公司的基本信息")
    @Valid
    private UpdateCompanyInfoDTO updateCompanyInfoDto;

    @ApiModelProperty(value = "公司的开票信息")
    @Valid
    private UpdetaInvoiceInfoDTO updetaInvoiceInfoDto;

    @ApiModelProperty(value = "公司的账户信息")
    @Valid
    private UpdateMerchantInfDTO updateMerchantInfDto;

    @ApiModelProperty(value = "公司的组织结构信息")
    @Valid
    private UpdateCooperationDTO updateCooperationDto;

    @ApiModelProperty(value = "合作信息")
    @Valid
    List<UpdateCompanyTaxDTO> updateCompanyTaxDtoList;
}
