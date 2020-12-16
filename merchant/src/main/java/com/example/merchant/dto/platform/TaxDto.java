package com.example.merchant.dto.platform;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.mybatis.entity.InvoiceLadderPrice;
import com.example.mybatis.entity.Tax;
import com.example.mybatis.entity.TaxPackage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@ApiModel(description = "添加服务商")
public class TaxDto {

    @ApiModelProperty(notes = "服务商ID(编辑时用)", value = "服务商ID(编辑时用)")
    private String id;

    /**
     * 公司的简称
     */
    @ApiModelProperty(notes = "公司的简称", value = "公司的简称")
    private String taxSName;

    /**
     * 公司的法定人
     */
    @ApiModelProperty(notes = "公司的法定人", value = "公司的法定人")
    private String taxMan;

    /**
     * 公司的营业执照
     */
    @ApiModelProperty(notes = "公司的营业执照", value = "公司的营业执照")
    private String businessLicense;

    /**
     * 公司全称
     */
    @ApiModelProperty(notes = "公司全称", value = "公司全称")
    private String taxName;

    /**
     * 公司的详细地址
     */
    @ApiModelProperty(notes = "公司的详细地址", value = "公司的详细地址")
    private String taxAddress;

    /**
     * 公司的成立时间
     */
    @ApiModelProperty(notes = "公司的成立时间", value = "公司的成立时间")
    private LocalDate taxCreateDate;

    /**
     * 公司联系人
     */
    @ApiModelProperty(notes = "公司联系人", value = "公司联系人")
    private String linkMan;

    /**
     * 公司联系电话
     */
    @ApiModelProperty(notes = "公司联系电话", value = "公司联系电话")
    private String linkMobile;

    /**
     * 统一的社会信用代码
     */
    @ApiModelProperty(notes = "统一的社会信用代码", value = "统一的社会信用代码")
    private String creditCode;

    /**
     * 公司状态0正常，1停用
     */
    @ApiModelProperty(notes = "公司状态0正常，1停用", value = "公司状态0正常，1停用")
    private Integer taxStatus;


    @ApiModelProperty(notes = "总包信息", value = "总包信息")
    private TaxPackageDto totalTaxPackage;

    @ApiModelProperty(notes = "众包信息", value = "众包信息")
    private TaxPackageDto manyTaxPackage;

    @ApiModelProperty(notes = "总包税率梯度价", value = "总包税率梯度价")
    private List<InvoiceLadderPriceDto> totalLadders;

    @ApiModelProperty(notes = "众包税率梯度价", value = "众包税率梯度价")
    private List<InvoiceLadderPriceDto> manyLadders;
}
