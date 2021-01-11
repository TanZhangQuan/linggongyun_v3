package com.example.merchant.dto.platform;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "XXXXX")
public class UpdateCompanyInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户ID")
    private String id;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "法人")
    private String companyMan;

    @ApiModelProperty(value = "注册资本")
    private BigDecimal registeredCapital;

    @ApiModelProperty(value = "成立日期")
    private LocalDate companyCreateDate;

    @ApiModelProperty(value = "统一的社会信用代码")
    private String creditCode;

    @ApiModelProperty(value = "开户行")
    private String bankName;

    @ApiModelProperty(value = "银行卡号")
    private String bankCode;

    @ApiModelProperty(value = "公司的营业执照")
    private String businessLicense;

    @ApiModelProperty(value = "加盟合同地址")
    private String contract;
}
