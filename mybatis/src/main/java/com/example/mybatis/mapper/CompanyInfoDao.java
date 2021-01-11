package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.entity.CompanyInfo;
import com.example.mybatis.po.CompanyPaymentOrderPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 公司信息
 * Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface CompanyInfoDao extends BaseMapper<CompanyInfo> {

    IPage<CompanyPaymentOrderPO> selectCompanyPaymentOrder(Page page, @Param("taxIds") List<String> taxIds, @Param("companyId") String companyId, @Param("taxName") String taxName, @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<CompanyPaymentOrderPO> exportCompanyPaymentOrder(@Param("paymentOrderIds") List<String> paymentOrderIds);
}
