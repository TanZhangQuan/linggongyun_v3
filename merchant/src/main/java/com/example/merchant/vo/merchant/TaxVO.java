package com.example.merchant.vo.merchant;

import com.example.mybatis.entity.CompanyLadderService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 服务商信息
 */
@Data
public class TaxVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "是否有梯度价：0没有，1有", value = "是否有梯度价：0没有，1有")
    private Integer chargeStatus;

    @ApiModelProperty(notes = "服务商名称", value = "服务商名称")
    private String taxName;

    @ApiModelProperty(notes = "合作类型：0总包，1众包", value = "合作类型：0总包，1众包")
    private Integer packageStatus;

    @ApiModelProperty(notes = "一口价", value = "一口价")
    private BigDecimal serviceCharge;

    @ApiModelProperty(notes = "合作合同url", value = "合作合同url")
    private String contract;

    @ApiModelProperty(notes = "收款人", value = "收款人")
    private String payee;

    @ApiModelProperty(notes = "开户行", value = "开户行")
    private String bankName;

    @ApiModelProperty(notes = "银行账号", value = "银行账号")
    private String bankCode;

    @ApiModelProperty(notes = "给商户的梯度价", value = "给商户的梯度价")
    private List<CompanyLadderService> companyLadderServices;
}
