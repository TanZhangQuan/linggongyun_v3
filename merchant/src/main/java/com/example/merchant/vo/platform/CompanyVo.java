package com.example.merchant.vo.platform;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/9
 */
@Data
public class CompanyVo {

    /**
     * 公司基本信息
     */
    private CompanyInfoVo companyInfoVo;

    /**
     * 公司的开票信息
     */
    private QueryInvoiceInfoVo queryInvoiceInfoVo;

    /**
     * 公司的账户信息
     */
    private QueryMerchantInfoVo queryMerchantInfoVo;

    /**
     * 公司的组织结构信息
     */
    private QueryCooperationInfoVo queryCooperationInfoVo;

    /**
     * 服务商合作信息
     */
    private List<CompanyTaxVo> companyTaxVoList;
}
