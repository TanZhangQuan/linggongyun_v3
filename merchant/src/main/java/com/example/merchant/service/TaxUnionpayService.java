package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.enums.UnionpayBankType;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.AddOrUpdateTaxUnionpayDTO;
import com.example.mybatis.entity.TaxUnionpay;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务商银联信息表 服务类
 * </p>
 *
 * @author hzp
 * @since 2021-01-08
 */
public interface TaxUnionpayService extends IService<TaxUnionpay> {

    /**
     * 添加或修改服务商银联
     *
     * @param addOrUpdateTaxUnionpayDTO
     * @return
     */
    ReturnJson addOrUpdateTaxUnionpay(AddOrUpdateTaxUnionpayDTO addOrUpdateTaxUnionpayDTO);

    /**
     * 开启或关闭服务商银联
     *
     * @param taxUnionpayId
     * @param boolEnable
     * @return
     */
    ReturnJson boolEnableTaxUnionpay(String taxUnionpayId, Boolean boolEnable);

    /**
     * 查询服务商-银联银行记录数
     *
     * @param taxUnionpayId
     * @param taxId
     * @param unionpayBankType
     * @return
     */
    int queryTaxUnionpayCount(String taxUnionpayId, String taxId, UnionpayBankType unionpayBankType);

    /**
     * 根据商户号查询服务商银联记录数
     *
     * @param taxUnionpayId
     * @param merchNo
     * @return
     */
    int queryTaxUnionpayMerchNoCount(String taxUnionpayId, String merchNo);

    /**
     * 查询服务商-银联银行记录
     *
     * @param taxId
     * @param unionpayBankType
     * @return
     */
    TaxUnionpay queryTaxUnionpay(String taxId, UnionpayBankType unionpayBankType);

    /**
     * 根据商户号查询服务商银联记录
     *
     * @param merchNo
     * @return
     */
    TaxUnionpay queryTaxUnionpayByMerchNo(String merchNo);

    /**
     * 查询服务商-银联银行记录
     *
     * @param taxId
     * @return
     */
    List<TaxUnionpay> queryTaxUnionpay(String taxId);

    /**
     * 查询服务商银联列表
     *
     * @param taxId
     * @return
     */
    ReturnJson queryTaxUnionpayList(String taxId);

    /**
     * 查询服务商拥有的银联支付方式
     *
     * @param taxId
     * @return
     */
    List<UnionpayBankType> queryTaxUnionpayMethod(String taxId);

    /**
     * 查询服务商银联余额
     *
     * @param taxUnionpayId
     * @return
     */
    ReturnJson queryTaxUnionpayBalance(String taxUnionpayId) throws Exception;

    /**
     * 查询服务商银联所有子账号的商户
     *
     * @param taxUnionpayId
     * @return
     */
    ReturnJson queryTaxUnionpayCompanyUnionpayList(String taxUnionpayId);

    /**
     * 服务商清分操作
     *
     * @param userId
     * @param taxUnionpayId
     * @param companyId
     * @param amount
     * @return
     * @throws Exception
     */
    ReturnJson clarify(String userId, String taxUnionpayId, String companyId, BigDecimal amount) throws Exception;
}
