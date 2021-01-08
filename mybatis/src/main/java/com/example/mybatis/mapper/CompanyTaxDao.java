package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.entity.CompanyTax;
import com.example.mybatis.vo.CompanyTaxMoneyVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-23
 */
public interface CompanyTaxDao extends BaseMapper<CompanyTax> {

    int getCompanyTax(String merchantId,String taxId,Integer packageStatus);

    List<CompanyTaxMoneyVO> getCompanyTaxMoney(String merchantId,String taxId,Integer packageStatus);
}
