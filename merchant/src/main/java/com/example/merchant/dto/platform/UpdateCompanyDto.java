package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/10
 */
@Data
public class UpdateCompanyDto {
    /**
     * 公司的基本信息
     */
    private UpdateCompanyInfoDto updateCompanyInfoDto;

    /**
     * 公司的开票信息
     */
    private UpdetaInvoiceInfoDto updetaInvoiceInfoDto;

    /**
     * 公司的账户信息
     */
    private UpdateMerchantInfDto updateMerchantInfDto;

    /**
     *公司的组织结构信息
     */
    private UpdateCooperationDto updateCooperationDto;

    /**
     * 合作信息
     */
    List<UpdateCompanyTaxDto> updateCompanyTaxDtoList;
}
