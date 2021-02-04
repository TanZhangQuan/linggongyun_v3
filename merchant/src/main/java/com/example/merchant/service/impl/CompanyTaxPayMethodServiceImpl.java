package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.PackageType;
import com.example.common.enums.PaymentMethod;
import com.example.merchant.service.CompanyTaxPayMethodService;
import com.example.mybatis.entity.CompanyTaxPayMethod;
import com.example.mybatis.mapper.CompanyTaxPayMethodDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商户服务商支付方式
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class CompanyTaxPayMethodServiceImpl extends ServiceImpl<CompanyTaxPayMethodDao, CompanyTaxPayMethod> implements CompanyTaxPayMethodService {

    @Override
    public CompanyTaxPayMethod queryCompanyTaxPayMethod(String companyId, String taxId, PackageType packageType, PaymentMethod paymentMethod) {
        QueryWrapper<CompanyTaxPayMethod> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CompanyTaxPayMethod::getCompanyId, companyId)
                .eq(CompanyTaxPayMethod::getTaxId, taxId)
                .eq(CompanyTaxPayMethod::getPackageType, packageType)
                .eq(CompanyTaxPayMethod::getPaymentMethod, paymentMethod);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public void closeCompanyTaxPayMethod(String companyId, String taxId) {
        QueryWrapper<CompanyTaxPayMethod> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CompanyTaxPayMethod::getCompanyId, companyId)
                .eq(CompanyTaxPayMethod::getTaxId, taxId);

        List<CompanyTaxPayMethod> companyTaxPayMethodList = baseMapper.selectList(queryWrapper);
        if (companyTaxPayMethodList != null && companyTaxPayMethodList.size() > 0) {
            for (CompanyTaxPayMethod companyTaxPayMethod : companyTaxPayMethodList){
                companyTaxPayMethod.setBoolEnable(false);
                updateById(companyTaxPayMethod);
            }
        }
    }
}
