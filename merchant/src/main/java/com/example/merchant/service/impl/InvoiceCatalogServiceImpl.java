package com.example.merchant.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.InvoiceCatalogService;
import com.example.mybatis.entity.InvoiceCatalog;
import com.example.mybatis.entity.PaymentOrder;
import com.example.mybatis.entity.PaymentOrderMany;
import com.example.mybatis.entity.TaxPackage;
import com.example.mybatis.mapper.InvoiceCatalogDao;
import com.example.mybatis.mapper.PaymentOrderDao;
import com.example.mybatis.mapper.PaymentOrderManyDao;
import com.example.mybatis.mapper.TaxPackageDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class InvoiceCatalogServiceImpl implements InvoiceCatalogService {
    @Resource
    private InvoiceCatalogDao invoiceCatalogDao;

    @Resource
    private PaymentOrderDao paymentOrderDao;

    @Resource
    private PaymentOrderManyDao paymentOrderManyDao;

    @Resource
    private TaxPackageDao taxPackageDao;

    @Override
    public ReturnJson getListInv(String id, Integer packageStatus) {
        List<String> list = Arrays.asList(id.split(","));
        if (packageStatus == 0) {
            PaymentOrder paymentOrder = paymentOrderDao.selectById(list.get(1));
            String supportCategory = taxPackageDao.selectOne(new QueryWrapper<TaxPackage>()
                    .eq("tax_id", paymentOrder.getTaxId())
                    .eq("package_status", packageStatus)).getSupportCategory();
            list = Arrays.asList(supportCategory.split(","));
        }
        if (packageStatus == 1) {
            PaymentOrderMany paymentOrderMany = paymentOrderManyDao.selectById(id);
            String supportCategory = taxPackageDao.selectOne(new QueryWrapper<TaxPackage>()
                    .eq("tax_id", paymentOrderMany.getTaxId())
                    .eq("package_status", packageStatus)).getSupportCategory();
            list = Arrays.asList(supportCategory.split(","));
        }

        List<InvoiceCatalog> invoiceCatalogList = invoiceCatalogDao.getListInv(list);
        return ReturnJson.success(invoiceCatalogList);
    }
}
