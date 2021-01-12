package com.example.merchant.dto.platform;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@ApiModel(description = "接收前端添加商户的参数")
public class CompanyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公司名称")
    @NotBlank(message = "公司名称不能为空")
    private String companyName;

    @ApiModelProperty(value = "公司的法定人")
    @NotBlank(message = "法定人不能为空")
    private String companyMan;

    @ApiModelProperty(value = "加盟合同")
    @NotBlank(message = "加盟合同不能为空")
    private String contract;

    @ApiModelProperty(value = "公司的成立时间")
    @NotBlank(message = "成立时间不能为空")
    private LocalDate companyCreateDate;

    @ApiModelProperty(value = "社会统一信用代码")
    @NotBlank(message = "社会统一信用代码不能为空")
    private String creditCode;

    @ApiModelProperty(value = "公司的营业执照")
    @NotBlank(message = "营业执照不能为空")
    private String businessLicense;

    @ApiModelProperty(value = "公司的注册资本")
    @NotBlank(message = "注册资本不能为空")
    private BigDecimal registeredCapital;

    @ApiModelProperty(value = "账户名称")
    @NotBlank(message = "账户名称不能为空")
    private String titleOfAccount;

    @ApiModelProperty(value = "开户行")
    @NotBlank(message = "开户行不能为空")
    private String bankName;

    @ApiModelProperty(value = "银行卡号")
    @NotBlank(message = "银行卡号不能为空")
    private String bankCode;

    @ApiModelProperty(value = "业务员ID", required = true)
    @NotBlank(message = "业务员不能为空")
    private String salesManId;

    @ApiModelProperty(value = "代理商ID")
    private String agentId;

    @ApiModelProperty(value = "服务商合作信息")
    @Valid
    List<CompanyTaxDTO> companyTaxDtos;

    @ApiModelProperty(value = "商户公司的开票信息")
    @Valid
    private AddCompanyInvoiceInfoDTO addCompanyInvoiceInfoDto;

    @ApiModelProperty(value = "商户公司的联系人")
    @Valid
    private AddLinkmanDTO addLinkmanDto;

    @ApiModelProperty(value = "商户公司的收货地址")
    @Valid
    private AddressDTO addressDto;

    @ApiModelProperty(value = "商户的登录信息")
    @Valid
    private AddMerchantDTO addMerchantDto;

}
