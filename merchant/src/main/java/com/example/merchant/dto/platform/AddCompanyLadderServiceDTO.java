package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "梯度价信息")
public class AddCompanyLadderServiceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户公司ID")
    @NotBlank(message = "商户公司ID不能为空")
    private String companyTaxId;

    @ApiModelProperty(value = "开始的金额")
    @NotNull(message = "开始的金额不能为空")
    private BigDecimal startMoney;

    @ApiModelProperty(value = "结束的金额")
    @NotNull(message = "结束的金额不能为空")
    private BigDecimal endMoney;

    @ApiModelProperty(value = "服务费（如7.5，不需把百分数换算成小数）")
    @NotNull(message = "服务费不能为空")
    @Min(value = 0,message = "服务费率不能小于零")
    private BigDecimal serviceCharge;
}
