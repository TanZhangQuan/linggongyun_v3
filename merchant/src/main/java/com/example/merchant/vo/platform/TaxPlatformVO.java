package com.example.merchant.vo.platform;

import com.example.mybatis.entity.InvoiceLadderPrice;
import com.example.mybatis.entity.TaxPackage;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Data
@ApiModel(description = "添加服务商")
public class TaxPlatformVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商ID")
    private String id;

    @ApiModelProperty(value = "公司全称")
    private String taxName;

    @ApiModelProperty(value = "统一的社会信用代码")
    private String creditCode;

    @ApiModelProperty(value = "公司的法定人")
    private String taxMan;

    @ApiModelProperty(value = "公司的详细地址")
    private String taxAddress;

    @ApiModelProperty(value = "公司的成立时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate taxCreateDate;

    @ApiModelProperty(value = "联系人名称")
    private String linkMan;

    @ApiModelProperty(value = "联系人职位")
    private String linkPosition;

    @ApiModelProperty(value = "联系人手机号")
    private String linkMobile;

    @ApiModelProperty(value = "联系人邮箱")
    private String linkEmail;

    @ApiModelProperty(value = "开票资料-公司名称")
    private String invoiceEnterpriseName;

    @ApiModelProperty(value = "开票资料-纳税识别号")
    private String invoiceTaxNo;

    @ApiModelProperty(value = "开票资料-地址电话")
    private String invoiceAddressPhone;

    @ApiModelProperty(value = "开票资料-开户行及账号")
    private String invoiceBankNameAccount;

    @ApiModelProperty(value = "收款单位名称")
    private String titleOfAccount;

    @ApiModelProperty(value = "收款单位账号")
    private String bankCode;

    @ApiModelProperty(value = "开户行名称")
    private String bankName;

    @ApiModelProperty(value = "收件人")
    private String receiptName;

    @ApiModelProperty(value = "收件人手机号")
    private String receiptPhone;

    @ApiModelProperty(value = "收件地址")
    private String receiptAddress;

    @ApiModelProperty(value = "营业执照")
    private String businessLicense;

    @ApiModelProperty(value = "法人身份证")
    private String taxManIdcard;

    @ApiModelProperty(value = "平台加盟合同")
    private String joinContract;

    @ApiModelProperty(value = "管理员账户号")
    private String userName;

    @ApiModelProperty(value = "管理员手机号")
    private String loginMobile;

    @ApiModelProperty(value = "网商银行会员号")
    private String memberId;

    @ApiModelProperty(value = "网商银行子账户唯一识别码")
    private String subAccountNo;

    @ApiModelProperty(value = "公司状态 0正常，1停用")
    private Integer taxStatus;

    @ApiModelProperty(value = "总包信息")
    private TaxPackage totalTaxPackage;

    @ApiModelProperty(value = "众包信息")
    private TaxPackage manyTaxPackage;

    @ApiModelProperty(value = "总包税率梯度价")
    private List<InvoiceLadderPrice> totalLadders;

    @ApiModelProperty(value = "众包税率梯度价")
    private List<InvoiceLadderPrice> manyLadders;
}
