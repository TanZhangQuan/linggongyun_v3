package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.TaxService;
import com.example.mybatis.entity.MerchantTax;
import com.example.mybatis.entity.Tax;
import com.example.mybatis.mapper.MerchantTaxDao;
import com.example.mybatis.mapper.TaxDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 查询商户可用使用的平台服务商
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getTaxAll(String merchantId) {
        List<MerchantTax> merchantTaxes = merchantTaxDao.selectList(new QueryWrapper<MerchantTax>().eq("merchant_id", merchantId));
        List<String> ids = new LinkedList<>();
        for (MerchantTax merchantTax : merchantTaxes ) {
            ids.add(merchantTax.getTaxId());
        }
        List<Tax> taxes = taxDao.selectList(new QueryWrapper<Tax>().in("id", ids).eq("tax_status", 0));
        return ReturnJson.success(taxes);
    }
}
