package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.TaxDto;
import com.example.merchant.service.TaxService;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 合作园区信息 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class TaxServiceImpl extends ServiceImpl<TaxDao, Tax> implements TaxService {

    @Autowired
    private TaxDao taxDao;

    @Autowired
    private MerchantTaxDao merchantTaxDao;

    @Autowired
    private InvoiceCatalogDao invoiceCatalogDao;

    @Autowired
    private TaxPackageDao taxPackageDao;

    /**
     * 查询商户可用使用的平台服务商
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getTaxAll(String merchantId, Integer packageStatus) {
        List<MerchantTax> merchantTaxes = merchantTaxDao.selectList(new QueryWrapper<MerchantTax>()
                .eq("merchant_id", merchantId).eq("package_status",packageStatus));
        List<String> ids = new LinkedList<>();
        for (MerchantTax merchantTax : merchantTaxes ) {
            ids.add(merchantTax.getTaxId());
        }
        List<Tax> taxes = taxDao.selectList(new QueryWrapper<Tax>().in("id", ids).eq("tax_status", 1));
        return ReturnJson.success(taxes);
    }

    @Override
    public ReturnJson getCatalogAll() {
        List<InvoiceCatalog> invoiceCatalogs = invoiceCatalogDao.selectList(new QueryWrapper<>());
        return ReturnJson.success(invoiceCatalogs);
    }

    @Override
    public ReturnJson saveCatalog(InvoiceCatalog invoiceCatalog) {
        int i = invoiceCatalogDao.insert(invoiceCatalog);
        if (i == 1) {
            return ReturnJson.success("添加类目成功！");
        }
        return ReturnJson.error("添加类目失败！");
    }

    @Autowired
    private IndustryDao industryDao;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson test(Industry industry) {
        industryDao.insert(industry);
        industryDao.insert(industry);
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson saveTax(TaxDto taxDto) {
        Tax tax = new Tax();
        BeanUtils.copyProperties(taxDto,tax);
        log.error(tax.toString());
        taxDao.insert(tax);
        TaxPackage totalTaxPackage = taxDto.getTotalTaxPackage();
        if (totalTaxPackage != null){
            totalTaxPackage.setPackageStatus(0);
            totalTaxPackage.setTaxId(tax.getId());
            taxPackageDao.insert(totalTaxPackage);
            List<InvoiceLadderPrice> totalLadders = taxDto.getTotalLadders();
            for (int i = 0; i < totalLadders.size(); i++) {
                if (i != totalLadders.size()-1){
                    InvoiceLadderPrice invoiceLadderPrice = totalLadders.get(i);
                    InvoiceLadderPrice invoiceLadderPriceNext = totalLadders.get(i+1);
                    invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney());
                    if (invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney()) < 0 ){
                        return ReturnJson.error("上梯度结束金额应小于下梯度起始金额");
                    }
                }
                totalLadders.get(i).setTaxId(tax.getId());
            }


        }


        TaxPackage manyTaxPackage = taxDto.getManyTaxPackage();
        manyTaxPackage.setPackageStatus(1);
        manyTaxPackage.setTaxId(tax.getId());


        return null;
    }
}
