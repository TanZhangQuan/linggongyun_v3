package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "添加或编辑服务商")
public class TaxDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商ID(编辑须传)")
    private String taxId;

    @ApiModelProperty(value = "公司名称")
    @NotBlank(message = "请输入公司名称")
    private String taxName;

    @ApiModelProperty(value = "统一社会信用代码")
    @NotBlank(message = "请输入统一社会信用代码")
    private String creditCode;
    
    @ApiModelProperty(value = "法定人")
    @NotBlank(message = "请输入法定人")
    private String taxMan;

//    @ApiModelProperty(value = "公司详细地址")
//    private String taxAddress;
//
//    @ApiModelProperty(value = "公司的成立时间")
//    private LocalDate taxCreateDate;

    @ApiModelProperty(value = "联系人名称")
    @NotBlank(message = "请输入联系人名称")
    private String linkMan;

    @ApiModelProperty(value = "联系人职位")
    @NotBlank(message = "请选择联系人职位")
    private String linkPosition;

    @ApiModelProperty(value = "联系人手机号")
    @NotBlank(message = "请输入联系人手机号")
    @Length(min = 11, max = 11, message = "请输入11位联系人手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的联系人手机号")
    private String linkMobile;

    @ApiModelProperty(value = "联系人邮箱")
    @NotBlank(message = "请输入联系人邮箱")
    @Email(message = "请输入正确的联系人邮箱")
    private String linkEmail;

    @ApiModelProperty(value = "开票-公司名称")
    @NotBlank(message = "请输入开票-公司名称")
    private String invoiceEnterpriseName;

    @ApiModelProperty(value = "开票-纳税识别号")
    @NotBlank(message = "请输入开票-纳税识别号")
    private String invoiceTaxNo;

    @ApiModelProperty(value = "开票-地址电话")
    @NotBlank(message = "请输入开票-地址电话")
    private String invoiceAddressPhone;

    @ApiModelProperty(value = "开票-开户行及账号")
    @NotBlank(message = "请输入开票-开户行及账号")
    private String invoiceBankNameAccount;

    @ApiModelProperty(value = "收款单位名称")
    @NotBlank(message = "请输入收款单位名称")
    private String titleOfAccount;

    @ApiModelProperty(value = "收款单位账号")
    @NotBlank(message = "请输入收款单位账号")
    private String bankCode;

    @ApiModelProperty(value = "开户行名称")
    @NotBlank(message = "请输入开户行名称")
    private String bankName;

    @ApiModelProperty(value = "收件人")
    @NotBlank(message = "请输入收件人")
    private String receiptName;

    @ApiModelProperty(value = "收件人手机号")
    @NotBlank(message = "请输入收件人手机号")
    @Length(min = 11, max = 11, message = "请输入11位收件人手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的收件人手机号")
    private String receiptPhone;

    @ApiModelProperty(value = "收件地址")
    @NotBlank(message = "请输入收件地址")
    private String receiptAddress;

    @ApiModelProperty(value = "营业执照")
    @NotBlank(message = "请上传营业执照")
    private String businessLicense;

    @ApiModelProperty(value = "法人身份证")
    private String taxManIdcard;

    @ApiModelProperty(value = "平台加盟合同")
    @NotBlank(message = "请上传平台加盟合同")
    private String joinContract;

    @ApiModelProperty(value = "管理员账户号")
    @NotBlank(message = "请输入管理员账户号")
    @Pattern(regexp = "[A-Z,a-z,0-9,-]*", message = "管理员账户号只能包含字母和数字")
    private String userName;

    @ApiModelProperty(value = "管理员手机号")
    @NotBlank(message = "请输入管理员手机号")
    @Length(min = 11, max = 11, message = "请输入11位管理员手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的管理员手机号")
    private String loginMobile;

    @ApiModelProperty(value = "管理员密码")
    private String passWord;

    @ApiModelProperty(value = "总包信息")
    private TaxPackageDTO totalTaxPackage;

    @ApiModelProperty(value = "众包信息")
    private TaxPackageDTO manyTaxPackage;

    @ApiModelProperty(value = "分包汇总代开（开票）税率梯度价")
    @Valid
    private List<InvoiceLadderPriceDTO> totalCollectLadders;

    @ApiModelProperty(value = "分包单人单开（开票）税率梯度价")
    @Valid
    private List<InvoiceLadderPriceDTO> totalSingleLadders;

    @ApiModelProperty(value = "总包（手续费）税率梯度价")
    @Valid
    private List<InvoiceLadderPriceDTO> totalServiceLadders;

    @ApiModelProperty(value = "众包（开票）税率梯度价")
    @Valid
    private List<InvoiceLadderPriceDTO> manyLadders;

    @ApiModelProperty(value = "众包（手续费）税率梯度价")
    @Valid
    private List<InvoiceLadderPriceDTO> manyServiceLadders;

}
