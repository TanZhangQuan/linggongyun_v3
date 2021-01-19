package com.example.merchant.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.enums.UnionpayBankType;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.CompanyUnionpay;

import java.util.List;

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
    CompanyUnionpay queryMerchantUnionpay(String companyId, String taxUnionpayId);

    /**
     * 查询商户-服务商银联银行记录
     *
     * @param companyId
     * @param taxUnionpayId
     * @param unionpayBankType
     * @return
     */
    CompanyUnionpay queryMerchantUnionpayUnionpayBankType(String companyId, String taxUnionpayId, UnionpayBankType unionpayBankType);

    /**
     * 查询商户拥有的银联支付方式
     *
     * @param companyId
     * @param taxId
     * @return
     */
    List<UnionpayBankType> queryCompanyUnionpayMethod(String companyId, String taxId);

    /**
     * 查询线下支付关联的服务商
     *
     * @param companyId
     * @param pageNo
     * @param pageSize
     * @return
     */
    ReturnJson queryOfflineTaxList(String companyId, long pageNo, long pageSize);

    /**
     * 查询银联支付关联的服务商
     *
     * @param companyId
     * @param pageNo
     * @param pageSize
     * @return
     */
    ReturnJson queryUninopayTaxList(String companyId, long pageNo, long pageSize);

    /**
     * 查询商户银联余额
     *
     * @param companyId
     * @param taxId
     * @return
     */
    ReturnJson queryCompanyUnionpayDetail(String companyId, String taxId, UnionpayBankType unionpayBankType) throws Exception;

}
