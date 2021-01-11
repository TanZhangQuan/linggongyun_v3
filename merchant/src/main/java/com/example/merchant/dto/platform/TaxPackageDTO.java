package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "服务商开包信息")
public class TaxPackageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "税号")
    private String invoiceTaxno;

    @ApiModelProperty(value = "税费率成本")
    @NotNull(message = "税费率成本不能为空")
    private BigDecimal taxPrice;

    @ApiModelProperty(value = "建议市场价最小值")
    @NotNull(message = "建议市场价最小值不能为空")
    private BigDecimal taxMinPrice;

    @ApiModelProperty(value = "建议市场价最大值")
    @NotNull(message = "建议市场价最大值不能为空")
    private BigDecimal taxMaxPrice;

    @ApiModelProperty(value = "收款方户名")
    @NotBlank(message = "收款方户名不能为空")
    private String payee;

    @ApiModelProperty(value = "银行名称")
    @NotBlank(message = "银行名称不能为空")
    private String bankName;

    @ApiModelProperty(value = "银行账号")
    @NotBlank(message = "银行账号不能为空")
    private String bankCode;

    @ApiModelProperty(value = "0总包，1众包")
    @NotNull(message = "请选择总包或者众包")
    private Integer packageStatus;

    @ApiModelProperty(value = "支持的类目ID 逗号分隔 全量更新")
    @NotBlank(message = "请选择开票类目")
    private String supportCategory;
}
