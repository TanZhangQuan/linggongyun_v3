package com.example.merchant.vo.platform;

import com.example.mybatis.entity.InvoiceLadderPrice;
import com.example.mybatis.entity.TaxPackage;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@ApiModel(description = "添加服务商")
public class TaxPlatformVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商ID")
    private String id;

    @ApiModelProperty(value = "公司的简称")
    private String taxSName;

    @ApiModelProperty(value = "公司的法定人")
    private String taxMan;

    @ApiModelProperty(value = "公司的营业执照")
    private String businessLicense;

    @ApiModelProperty(value = "公司全称")
    private String taxName;

    @ApiModelProperty(value = "公司的详细地址")
    private String taxAddress;

    @ApiModelProperty(value = "公司的成立时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate taxCreateDate;

    @ApiModelProperty(value = "公司联系人")
    private String linkMan;

    @ApiModelProperty(value = "公司联系电话")
    private String linkMobile;

    @ApiModelProperty(value = "统一的社会信用代码")
    private String creditCode;

    @ApiModelProperty(value = "公司状态0正常，1停用")
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
