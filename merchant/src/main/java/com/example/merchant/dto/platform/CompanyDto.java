package com.example.merchant.dto.platform;

import com.example.mybatis.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "接收前端添加商户的参数")
public class CompanyDto {
    /**
     * 公司姓名
     */
    @ApiModelProperty(notes = "公司名称", value = "公司名称")
    private String companyName;

    /**
     * 公司的法定人
     */
    @ApiModelProperty(notes = "公司的法定人", value = "公司的法定人")
    private String companyMan;

    /**
     * 加盟合同
     */
    @ApiModelProperty(notes = "加盟合同", value = "加盟合同")
    private String contract;

    /**
     * 公司的成立时间
     */
    @ApiModelProperty(notes = "公司的成立时间", value = "公司的成立时间")
    private LocalDateTime companyCreateDate;

    /**
     * 社会统一信用代码
     */
    @ApiModelProperty(notes = "社会统一信用代码", value = "社会统一信用代码")
    private String creditCode;

    /**
     * 公司的营业执照
     */
    @ApiModelProperty(notes = "公司的营业执照", value = "公司的营业执照")
    private String businessLicense;

    /**
     * 公司的注册资本
     */
    @ApiModelProperty(notes = "公司的注册资本", value = "公司的注册资本")
    private BigDecimal registeredCapital;

    /**
     * 业务员ID(必填)
     */
    @ApiModelProperty(notes = "业务员ID", value = "业务员ID", required = true)
    @NotBlank(message = "业务员不能为空！")
    private String salesManId;

    /**
     * 代理商ID(可以为空)
     */
    @ApiModelProperty(notes = "代理商ID", value = "代理商ID")
    private String agentId;

    @ApiModelProperty(notes = "服务商合作信息", value = "服务商合作信息")
    @Valid
    List<CompanyTaxDto> companyTaxDtos;

    @ApiModelProperty(notes = "商户公司的开票信息", value = "商户公司的开票信息")
    private CompanyInvoiceInfo companyInvoiceInfo;

    @ApiModelProperty(notes = "商户公司的联系人", value = "商户公司的联系人")
    private Linkman linkman;

    @ApiModelProperty(notes = "商户公司的收货地址", value = "商户公司的收货地址")
    private Address address;

    @ApiModelProperty(notes = "商户的登录信息", value = "商户的登录信息")
    private Merchant merchant;

}
