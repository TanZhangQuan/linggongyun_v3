package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.entity.Tax;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.po.MerchantPaymentListPO;
import com.example.mybatis.po.RegulatorTaxPayInfoPo;
import com.example.mybatis.po.TaxListPO;
import com.example.mybatis.po.TaxPO;
import com.example.mybatis.vo.CooperationInfoVO;
import com.example.mybatis.vo.SellerVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务商公司信息
 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-21
 */
public interface TaxDao extends BaseMapper<Tax> {
    //销售方查询
    SellerVo getSellerById(String id);

    List<TaxPO> selectByMerchantId(String companyId);

    IPage<TaxListPO> selectTaxList(Page page, @Param("taxName") String taxName, @Param("startDate") String startDate, @Param("endDate") String endDate);
    IPage<MerchantPaymentListPO> selectTaxPaymentList(Page page, @Param("paymentOrderIds") List<String> paymentOrderIds);

    IPage<RegulatorTaxPayInfoPo> selectPayInfo(Page page,@Param("taxId")String taxId,@Param("companySName") String companySName, @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<RegulatorTaxPayInfoPo> getPayInfoByIds(@Param("payIds") List<String> payIds);

    List<CooperationInfoVO> queryCooper(String companyId);
}
