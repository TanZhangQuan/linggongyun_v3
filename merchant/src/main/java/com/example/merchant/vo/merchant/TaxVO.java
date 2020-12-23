package com.example.merchant.vo.merchant;

import com.example.mybatis.entity.CompanyLadderService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class TaxVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "是否有梯度价：0没有，1有")
    private Integer chargeStatus;

    @ApiModelProperty(value = "服务商名称")
    private String taxName;

    @ApiModelProperty(value = "合作类型：0总包，1众包")
    private Integer packageStatus;

    @ApiModelProperty(value = "一口价")
    private BigDecimal serviceCharge;

    @ApiModelProperty(value = "合作合同url")
    private String contract;

    @ApiModelProperty(value = "收款人")
    private String payee;

    @ApiModelProperty(value = "开户行")
    private String bankName;

    @ApiModelProperty(value = "银行账号")
    private String bankCode;

    @ApiModelProperty(value = "给商户的梯度价")
    private List<CompanyLadderService> companyLadderServices;
}
