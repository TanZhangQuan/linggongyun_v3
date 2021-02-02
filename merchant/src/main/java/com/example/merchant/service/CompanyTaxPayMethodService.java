package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.enums.PackageType;
import com.example.common.enums.PaymentMethod;
import com.example.mybatis.entity.CompanyTaxPayMethod;

/**
 * <p>
 * 商户服务商支付方式
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface CompanyTaxPayMethodService extends IService<CompanyTaxPayMethod> {

    /**
     * 查询商户-服务商支付方式
     *
     * @param companyId
     * @param taxId
     */
    CompanyTaxPayMethod queryCompanyTaxPayMethod(String companyId, String taxId, PackageType packageType, PaymentMethod paymentMethod);

    /**
     * 商户-服务商支付方式全部不启用
     *
     * @param companyId
     * @param taxId
     */
    void closeCompanyTaxPayMethod(String companyId, String taxId);
}
