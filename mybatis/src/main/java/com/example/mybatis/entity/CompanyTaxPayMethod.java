package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.enums.PackageType;
import com.example.common.enums.PaymentMethod;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商户服务商支付方式
 * </p>
 *
 * @author tzq
 * @since 2021-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_company_tax_pay_method")
public class CompanyTaxPayMethod extends BaseEntity {

    /**
     * 商户ID
     */
    private String companyId;

    /**
     * 服务商ID
     */
    private String taxId;

    /**
     * 合作类型
     */
    private PackageType packageType;

    /**
     * 交易方式
     */
    private PaymentMethod paymentMethod;

    /**
     * 是否启用
     */
    private Boolean boolEnable;

}
