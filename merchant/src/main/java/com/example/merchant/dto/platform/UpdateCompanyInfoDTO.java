package com.example.merchant.dto.platform;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "修改公司基本信息")
public class UpdateCompanyInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户ID")
    @NotBlank(message = "商户ID不能为空")
    private String id;

    @ApiModelProperty(value = "公司名称")
    @NotBlank(message = "公司名称不能为空")
    private String companyName;

    @ApiModelProperty(value = "法人")
    @NotBlank(message = "法人不能为空")
    private String companyMan;

    @ApiModelProperty(value = "注册资本")
    @NotNull(message = "注册资本不能为空")
    private BigDecimal registeredCapital;

    @ApiModelProperty(value = "成立日期")
    @NotNull(message = "成立日期不能为空")
    private LocalDate companyCreateDate;

    @ApiModelProperty(value = "统一的社会信用代码")
    @NotBlank(message = "社会信用代码不能为空")
    private String creditCode;

    @ApiModelProperty(value = "开户行")
    @NotBlank(message = "开户行不能为空")
    private String bankName;

    @ApiModelProperty(value = "银行卡号")
    @NotBlank(message = "银行卡号不能为空")
    private String bankCode;

    @ApiModelProperty(value = "公司的营业执照")
    @NotBlank(message = "营业执照不能为空")
    private String businessLicense;

    @ApiModelProperty(value = "加盟合同地址")
    @NotBlank(message = "加盟合同地址不能为空")
    private String contract;
}
