package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.merchant.service.InvoiceLadderPriceService;
import com.example.merchant.service.TaxPackageService;
import com.example.mybatis.entity.InvoiceLadderPrice;
import com.example.mybatis.entity.TaxPackage;
import com.example.mybatis.mapper.TaxPackageDao;
import com.example.mybatis.vo.TaxPackageVO;
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
public class TaxPackageServiceImpl extends ServiceImpl<TaxPackageDao, TaxPackage> implements TaxPackageService {

    @Resource
    private InvoiceLadderPriceService invoiceLadderPriceService;

    @Override
    public void deleteTaxPackage(String taxId) {
        //搜索服务商总包众包合作信息记录
        QueryWrapper<TaxPackage> taxPackageQueryWrapper = new QueryWrapper<>();
        taxPackageQueryWrapper.lambda().eq(TaxPackage::getTaxId, taxId);
        List<TaxPackage> taxPackageList = baseMapper.selectList(taxPackageQueryWrapper);

        if (taxPackageList != null && taxPackageList.size() > 0) {
            for (TaxPackage taxPackage : taxPackageList) {
                //删除服务商总包众包合作信息:梯度价记录
                QueryWrapper<InvoiceLadderPrice> invoiceLadderPriceQueryWrapper = new QueryWrapper<>();
                invoiceLadderPriceQueryWrapper.lambda().eq(InvoiceLadderPrice::getTaxPackageId, taxPackage.getId());
                List<InvoiceLadderPrice> invoiceLadderPriceList = invoiceLadderPriceService.list(invoiceLadderPriceQueryWrapper);
                if (invoiceLadderPriceList != null && invoiceLadderPriceList.size() > 0) {
                    for (InvoiceLadderPrice invoiceLadderPrice : invoiceLadderPriceList) {
                        invoiceLadderPriceService.removeById(invoiceLadderPrice);
                    }
                }
                //删除服务商总包众包合作信息:一口价记录
                removeById(taxPackage.getId());
            }
        }
    }

    @Override
    public TaxPackage queryTaxPackage(String taxId, Integer packageStatus) {

        QueryWrapper<TaxPackage> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TaxPackage::getTaxId, taxId).eq(TaxPackage::getPackageStatus, packageStatus);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<TaxPackage> queryTaxPackageAll() {
        QueryWrapper<TaxPackage> queryWrapper = new QueryWrapper<>();
        return baseMapper.selectList(queryWrapper);
    }

}
