package com.example.merchant.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.MerchantUnionpay;

/**
 * <p>
 * 商户银联信息表 服务类
 * </p>
 *
 * @author hzp
 * @since 2021-01-08
 */
public interface MerchantUnionpayService extends IService<MerchantUnionpay> {

    /**
     * 查询商户-服务商银联银行记录
     *
     * @param merchantId
     * @param taxUnionpayId
     * @return
     */
    MerchantUnionpay queryMerchantUnionpay(String merchantId, String taxUnionpayId);

    /**
     * 查询线下支付关联的服务商
     *
     * @param merchantId
     * @param pageNo
     * @param pageSize
     * @return
     */
    ReturnJson queryOfflineTaxList(String merchantId, long pageNo, long pageSize);

    /**
     * 查询银联支付关联的服务商
     *
     * @param merchantId
     * @param pageNo
     * @param pageSize
     * @return
     */
    ReturnJson queryUninopayTaxList(String merchantId, long pageNo, long pageSize);

    /**
     * 查询商户银联余额
     *
     * @param merchantId
     * @param taxId
     * @return
     */
    ReturnJson queryCompanyUnionpayDetail(String merchantId, String taxId) throws Exception;

}
