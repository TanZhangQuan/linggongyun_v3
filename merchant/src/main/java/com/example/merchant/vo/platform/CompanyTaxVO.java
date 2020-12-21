package com.example.merchant.vo.platform;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yixi
 */
@Data
public class CompanyTaxVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 公司ID
     */
    private String companyId;

    /**
     * 服务商ID
     */
    private String taxId;

    /**
     * 费用类型0为一口价，1为梯度价
     */
    private Integer chargeStatus;

    /**
     * 一口价的服务费(如果为梯度价这为空)
     */
    private BigDecimal serviceCharge;

    /**
     * 合作类型
     */
    private Integer packageStatus;

    /**
     * 合作合同地址
     */
    private String contract;

    /**
     * 梯度价
     */
    private List<CompanyLadderServiceVO> companyLadderServiceVOList;

}
