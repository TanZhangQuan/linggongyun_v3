package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.enums.UnionpayBankType;
import com.example.mybatis.entity.CompanyUnionpay;
import com.example.mybatis.vo.UnionpayTaxListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商户银联信息表 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2021-01-08
 */
@Mapper
public interface CompanyUnionpayDao extends BaseMapper<CompanyUnionpay> {

    /**
     * 查询线下支付关联的服务商
     *
     * @param page
     * @param companyId
     * @return
     */
    IPage<UnionpayTaxListVO> queryOfflineTaxList(Page page, @Param("companyId") String companyId);

    /**
     * 查询银联支付关联的服务商
     *
     * @param page
     * @param companyId
     * @return
     */
    IPage<UnionpayTaxListVO> queryUninopayTaxList(Page page, @Param("companyId") String companyId);

    /**
     * 查询商户-服务商银联银行记录
     *
     * @param companyId
     * @param taxUnionpayId
     * @param unionpayBankType
     * @return
     */
    CompanyUnionpay queryMerchantUnionpayUnionpayBankType(@Param("companyId") String companyId, @Param("taxUnionpayId") String taxUnionpayId, @Param("unionpayBankType") UnionpayBankType unionpayBankType);

    /**
     * 查询商户拥有的银联支付方式
     *
     * @param companyId
     * @param taxId
     * @return
     */
    List<UnionpayBankType> queryCompanyUnionpayMethod(@Param("companyId") String companyId, @Param("taxId") String taxId);
}
