package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.merchant.service.CompanyLadderServiceService;
import com.example.merchant.service.CompanyTaxService;
import com.example.mybatis.entity.CompanyLadderService;
import com.example.mybatis.entity.CompanyTax;
import com.example.mybatis.mapper.CompanyTaxDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-23
 */
@Service
public class CompanyTaxServiceImpl extends ServiceImpl<CompanyTaxDao, CompanyTax> implements CompanyTaxService {

    @Resource
    private CompanyLadderServiceService companyLadderServiceService;

    @Override
    public void deleteCompanyTaxByTax(String taxId) {

        //搜索所有商户-服务商总包众包合作信息记录
        QueryWrapper<CompanyTax> companyTaxQueryWrapper = new QueryWrapper<>();
        companyTaxQueryWrapper.lambda().eq(CompanyTax::getTaxId, taxId);
        List<CompanyTax> companyTaxList = baseMapper.selectList(companyTaxQueryWrapper);

        if (companyTaxList != null && companyTaxList.size() > 0) {
            for (CompanyTax companyTax : companyTaxList) {
                //删除商户-服务商总包众包合作信息:梯度价记录
                QueryWrapper<CompanyLadderService> companyLadderServiceQueryWrapper = new QueryWrapper<>();
                companyLadderServiceQueryWrapper.lambda().eq(CompanyLadderService::getCompanyTaxId, companyTax.getId());
                List<CompanyLadderService> companyLadderServiceList = companyLadderServiceService.list(companyLadderServiceQueryWrapper);
                if (companyLadderServiceList != null && companyLadderServiceList.size() > 0) {
                    for (CompanyLadderService companyLadderService : companyLadderServiceList) {
                        companyLadderServiceService.removeById(companyLadderService);
                    }
                }
                //删除商户-服务商总包众包合作信息:一口价记录
                removeById(companyTax.getId());
            }
        }
    }

    @Override
    public void deleteCompanyTaxByCompany(String companyId) {

        //搜索所有商户-服务商总包众包合作信息记录
        QueryWrapper<CompanyTax> companyTaxQueryWrapper = new QueryWrapper<>();
        companyTaxQueryWrapper.lambda().eq(CompanyTax::getCompanyId, companyId);
        List<CompanyTax> companyTaxList = baseMapper.selectList(companyTaxQueryWrapper);

        if (companyTaxList != null && companyTaxList.size() > 0) {
            for (CompanyTax companyTax : companyTaxList) {
                //删除商户-服务商总包众包合作信息:梯度价记录
                QueryWrapper<CompanyLadderService> companyLadderServiceQueryWrapper = new QueryWrapper<>();
                companyLadderServiceQueryWrapper.lambda().eq(CompanyLadderService::getCompanyTaxId, companyTax.getId());
                List<CompanyLadderService> companyLadderServiceList = companyLadderServiceService.list(companyLadderServiceQueryWrapper);
                if (companyLadderServiceList != null && companyLadderServiceList.size() > 0) {
                    for (CompanyLadderService companyLadderService : companyLadderServiceList) {
                        companyLadderServiceService.removeById(companyLadderService);
                    }
                }
                //删除商户-服务商总包众包合作信息:一口价记录
                removeById(companyTax.getId());
            }
        }

    }

    @Override
    public CompanyTax queryCompanyTax(String taxId, String companyId, Integer packageStatus) {

        QueryWrapper<CompanyTax> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CompanyTax::getTaxId, taxId).eq(CompanyTax::getCompanyId, companyId).eq(CompanyTax::getPackageStatus, packageStatus);

        return baseMapper.selectOne(queryWrapper);
    }

}
