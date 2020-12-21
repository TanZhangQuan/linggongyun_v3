package com.example.merchant.vo.merchant;

import com.example.mybatis.entity.CompanyLadderService;
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

    /**
     * 是否有梯度价：0没有，1有
     */
    private Integer chargeStatus;

    /**
     * 服务商名称
     */
    private String taxName;

    /**
     * 合作类型：0总包，1众包
     */
    private Integer packageStatus;

    /**
     * 一口价
     */
    private BigDecimal serviceCharge;

    /**
     * 合作合同
     */
    private String contract;

    /**
     * 收款人
     */
    private String payee;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankCode;

    /**
     * 给商户的梯度价
     */
    private List<CompanyLadderService> companyLadderServices;
}
