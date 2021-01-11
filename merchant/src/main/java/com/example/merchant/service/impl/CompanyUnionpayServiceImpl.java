package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.merchant.service.CompanyUnionpayService;
import com.example.mybatis.entity.CompanyUnionpay;
import com.example.mybatis.mapper.CompanyUnionpayDao;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商户银联信息表 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2021-01-08
 */
@Service
public class CompanyUnionpayServiceImpl extends ServiceImpl<CompanyUnionpayDao, CompanyUnionpay> implements CompanyUnionpayService {

    @Override
    public CompanyUnionpay queryCompanyUnionpay(String companyId, String taxUnionpayId) {

        QueryWrapper<CompanyUnionpay> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CompanyUnionpay::getCompanyId, companyId)
                .eq(CompanyUnionpay::getTaxUnionpayId, taxUnionpayId);

        return baseMapper.selectOne(queryWrapper);
    }
}
