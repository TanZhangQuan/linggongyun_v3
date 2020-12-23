package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXX")
public class TaxPackageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "税号")
    private String invoiceTaxno;

    @ApiModelProperty(value = "税费率成本")
    private BigDecimal taxPrice;

    @ApiModelProperty(value = "建议市场价最小值")
    private BigDecimal taxMinPrice;

    @ApiModelProperty(value = "建议市场价最大值")
    private BigDecimal taxMaxPrice;

    @ApiModelProperty(value = "收款方户名")
    private String payee;

    @ApiModelProperty(value = "银行名称")
    private String bankName;

    @ApiModelProperty(value = "银行账号")
    private String bankCode;

    @ApiModelProperty(value = "0总包，1众包")
    private Integer packageStatus;

    @ApiModelProperty(value = "支持的类目ID 逗号分隔 全量更新")
    private String supportCategory;
}
