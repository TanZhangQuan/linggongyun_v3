package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.entity.CompanyLadderService;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 * 服务商给商户的梯度价 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-23
 */
public interface CompanyLadderServiceDao extends BaseMapper<CompanyLadderService> {

    BigDecimal queryServiceCharge(@Param("companyTaxId") String companyTaxId, @Param("startMoney") BigDecimal startMoney, @Param("endMoney") BigDecimal endMoney);
}
