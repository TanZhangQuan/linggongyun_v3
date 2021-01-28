package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatis.entity.CompanyTax;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-23
 */
public interface CompanyTaxService extends IService<CompanyTax> {

    /**
     * 删除商户-服务商总包众包合作信息
     *
     * @param taxId
     */
    void deleteCompanyTax(String taxId);

    /**
     * 查询商户-服务商总包众包合作信息
     *
     * @param taxId
     * @param companyId
     * @param packageStatus
     * @return
     */
    CompanyTax queryCompanyTax(String taxId, String companyId, Integer packageStatus);
}
