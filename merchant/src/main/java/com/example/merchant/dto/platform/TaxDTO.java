package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@ApiModel(description = "添加或编辑服务商")
public class TaxDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商ID(编辑须传)")
    private String id;

    @ApiModelProperty(value = "公司的简称")
    private String taxSName;

    @ApiModelProperty(value = "公司的法定人")
    private String taxMan;

    @ApiModelProperty(value = "公司的营业执照")
    @NotBlank(message = "营业执照不能为空")
    private String businessLicense;

    @ApiModelProperty(value = "公司全称")
    @NotBlank(message = "公司全称不能为空")
    private String taxName;

    @ApiModelProperty(value = "公司的详细地址")
    private String taxAddress;

    @ApiModelProperty(value = "公司的成立时间")
    private LocalDate taxCreateDate;

    @ApiModelProperty(value = "公司联系人")
    private String linkMan;

    @ApiModelProperty(value = "公司联系电话")
    private String linkMobile;

    @ApiModelProperty(value = "统一的社会信用代码")
    @NotBlank(message = "社会信用代码不能为空")
    private String creditCode;

    @ApiModelProperty(value = "开户银行")
    @NotBlank(message = "开户行不能为空")
    private String bankName;

    @ApiModelProperty(value = "账户号码")
    @NotBlank(message = "银行号码不能为空")
    private String bankCode;

    @ApiModelProperty(value = "账户名称")
    @NotBlank(message = "收款名称不能为空")
    private String titleOfAccount;

    @ApiModelProperty(value = "公司状态0正常，1停用")
    private Integer taxStatus;

    @ApiModelProperty(value = "总包信息")
    private TaxPackageDTO totalTaxPackage;

    @ApiModelProperty(value = "众包信息")
    private TaxPackageDTO manyTaxPackage;

    @ApiModelProperty(value = "总包税率梯度价")
    @Valid
    private List<InvoiceLadderPriceDTO> totalLadders;

    @ApiModelProperty(value = "众包税率梯度价")
    @Valid
    private List<InvoiceLadderPriceDTO> manyLadders;
}
