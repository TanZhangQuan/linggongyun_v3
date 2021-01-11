package com.example.merchant.dto.platform;

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
    private String companyName;

    @ApiModelProperty(value = "公司的法定人")
    private String companyMan;

    @ApiModelProperty(value = "加盟合同")
    private String contract;

    @ApiModelProperty(value = "公司的成立时间")
    private LocalDate companyCreateDate;

    @ApiModelProperty(value = "社会统一信用代码")
    private String creditCode;

    @ApiModelProperty(value = "公司的营业执照")
    private String businessLicense;

    @ApiModelProperty(value = "公司的注册资本")
    private BigDecimal registeredCapital;

    @ApiModelProperty(value = "开户行")
    private String bankName;

    @ApiModelProperty(value = "银行卡号")
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
    private AddCompanyInvoiceInfoDTO addCompanyInvoiceInfoDto;

    @ApiModelProperty(value = "商户公司的联系人")
    private AddLinkmanDTO addLinkmanDto;

    @ApiModelProperty(value = "商户公司的收货地址")
    private AddressDTO addressDto;

    @ApiModelProperty(value = "商户的登录信息")
    private AddMerchantDTO addMerchantDto;

}
