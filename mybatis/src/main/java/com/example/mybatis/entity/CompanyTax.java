package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author hzp
 * @since 2020-09-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_company_tax")
public class CompanyTax extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    private String companyId;

    /**
     * 服务商ID
     */
    private String taxId;

    /**
     * 费用类型 0为一口价 1为梯度价
     */
    private Integer chargeStatus;

    /**
     * 一口价综合税费率(如果有梯度价这为空)
     */
    private BigDecimal serviceCharge;

    /**
     * 合作类型 0总包，1众包
     */
    private Integer packageStatus;

    /**
     * 合作合同地址
     */
    private String contract;

}
