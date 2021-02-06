package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatis.entity.TaxPackage;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-23
 */
public interface TaxPackageService extends IService<TaxPackage> {

    /**
     * 删除服务商总包众包合作信息
     *
     * @param taxId
     */
    void deleteTaxPackage(String taxId);

    /**
     * 查询服务商总包或众包合作信息
     *
     * @param taxId
     * @param packageStatus
     * @return
     */
    TaxPackage queryTaxPackage(String taxId, Integer packageStatus);


    /**
     * 查询服务商总包或众包合作信息
     *
     * @return
     */
    List<TaxPackage> queryTaxPackageAll();

}
