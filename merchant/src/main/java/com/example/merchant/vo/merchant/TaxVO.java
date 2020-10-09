package com.example.merchant.vo.merchant;

import com.example.mybatis.entity.CompanyLadderService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 服务商信息
 */
@Data
public class TaxVO {
    private Integer chargeStatus;
    private String taxName;
    private Integer packageStatus;
    private BigDecimal serviceCharge;
    /**
     * 合作合同地址
     */
    private String contract;
    private String payee;
    private String bankName;
    private String bankCode;
    @ApiModelProperty(notes = "给商户的梯度价", value = "给商户的梯度价")
    private List<CompanyLadderService> companyLadderServices;
}
