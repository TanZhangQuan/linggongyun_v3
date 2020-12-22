package com.example.merchant.vo.platform;

import com.example.mybatis.vo.CooperationInfoVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/9
 */
@Data
public class CompanyVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 公司基本信息
     */
    private CompanyInfoVO companyInfoVo;

    /**
     * 公司的开票信息
     */
    private QueryInvoiceInfoVO queryInvoiceInfoVo;

    /**
     * 公司的账户信息
     */
    private QueryMerchantInfoVO queryMerchantInfoVo;

    /**
     * 公司的组织结构信息
     */
    private QueryCooperationInfoVO queryCooperationInfoVo;

    /**
     * 服务商合作信息
     */
    private List<CooperationInfoVO> cooperationInfoVOList;
}
