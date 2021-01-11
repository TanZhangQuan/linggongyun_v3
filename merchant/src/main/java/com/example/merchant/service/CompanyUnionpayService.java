package com.example.merchant.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatis.entity.CompanyUnionpay;

/**
 * <p>
 * 商户银联信息表 服务类
 * </p>
 *
 * @author hzp
 * @since 2021-01-08
 */
public interface CompanyUnionpayService extends IService<CompanyUnionpay> {

    /**
     * 查询商户-服务商银联银行记录
     *
     * @param companyId
     * @param taxUnionpayId
     * @return
     */
    CompanyUnionpay queryCompanyUnionpay(String companyId, String taxUnionpayId);

}
