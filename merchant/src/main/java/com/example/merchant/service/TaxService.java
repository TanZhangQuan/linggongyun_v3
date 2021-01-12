package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.AddInvoiceCatalogDTO;
import com.example.merchant.dto.platform.TaxDTO;
import com.example.merchant.dto.platform.TaxListDTO;
import com.example.mybatis.entity.Tax;

/**
 * <p>
 * 合作园区信息 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface TaxService extends IService<Tax> {
    /**
     * 查询商户可用使用的平台服务商
     *
     * @param merchantId
     * @param packageStatus
     * @return
     */
    ReturnJson getTaxAll(String merchantId, Integer packageStatus);

    /**
     * 查询所有的平台服务商
     *
     * @param companyId
     * @param packageStatus
     * @return
     */
    ReturnJson getTaxPaasAll(String companyId, Integer packageStatus);

    /**
     * 查询所有开票类目
     *
     * @return
     */
    ReturnJson getCatalogAll();

    /**
     * 添加开票类目
     *
     * @param addInvoiceCatalogDto
     * @return
     */
    ReturnJson saveCatalog(AddInvoiceCatalogDTO addInvoiceCatalogDto);

    /**
     * 添加服务商
     *
     * @param taxDto
     * @return
     */
    ReturnJson saveTax(TaxDTO taxDto) throws Exception;

    /**
     * 查询服务商列表
     *
     * @param taxListDto
     * @return
     */
    ReturnJson getTaxList(TaxListDTO taxListDto);

    /**
     * 查询服务商详情
     *
     * @param taxId
     * @return
     */
    ReturnJson getTaxInfo(String taxId);

    /**
     * 查询交易流水统计
     *
     * @param taxId
     * @return
     */
    ReturnJson transactionRecordCount(String taxId);

    /**
     * 查询具体的交易流水
     *
     * @param taxId
     * @param page
     * @param pageSize
     * @return
     */
    ReturnJson transactionRecord(String taxId, Integer page, Integer pageSize);

    /**
     * 销售方信息
     *
     * @param id
     * @return
     */
    ReturnJson getSellerById(String id);

    /**
     * 查询所有可用的服务商
     *
     * @return
     */
    ReturnJson getTaxPaasList();

    /**
     * 查询所有可用的服务商
     *
     * @return
     */
    ReturnJson getTaxList(Integer packageStatus);

    /**
     * 查询服务商线下来款银行账号信息
     *
     * @param taxId
     * @return
     */
    ReturnJson queryTaxInBankInfo(String taxId);
}
