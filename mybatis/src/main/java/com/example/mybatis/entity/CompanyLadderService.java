package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>
 * 服务商给商户的梯度价
 * </p>
 *
 * @author hzp
 * @since 2020-09-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_company_ladder_service")
public class CompanyLadderService extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商户公司ID
     */
    private String companyTaxId;

    /**
     * 开始的金额
     */
    private BigDecimal startMoney;

    /**
     * 结束的金额
     */
    private BigDecimal endMoney;

    /**
     * 服务费（如7.5，不需把百分数换算成小数）
     */
    private BigDecimal serviceCharge;

}
