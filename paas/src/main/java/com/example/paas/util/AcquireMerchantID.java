package com.example.paas.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatis.entity.Managers;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.entity.MerchantTax;
import com.example.mybatis.entity.Tax;
import com.example.mybatis.mapper.ManagersDao;
import com.example.mybatis.mapper.MerchantTaxDao;
import com.example.mybatis.mapper.TaxDao;
import com.example.paas.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取当前管理人员的所以商户ID
 */
@Component
public class AcquireMerchantID {
    @Autowired
    private TaxDao taxDao;

    @Autowired
    private MerchantTaxDao merchantTaxDao;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private ManagersDao managersDao;

    public List<String> getMerchantIds(String managersId) {
        List<String> merchantIds = new ArrayList<>();
        Managers managers = managersDao.selectById(managersId);
        Integer userSign = managers.getUserSign();
        if (userSign == 1) {//管理人员为代理商
            List<Merchant> merchants = merchantService.list(new QueryWrapper<Merchant>().eq("agent_id", managers.getId()));
            for (Merchant merchant : merchants) {
                merchantIds.add(merchant.getId());
            }
        } else if (userSign == 2) {//管理人员为业务员
            List<Merchant> merchants = merchantService.list(new QueryWrapper<Merchant>().eq("sales_man_id", managers.getId()));
            for (Merchant merchant : merchants) {
                merchantIds.add(merchant.getId());
            }
        } else if (userSign == 3) {//管理人员为服务商
            Tax tax = taxDao.selectOne(new QueryWrapper<Tax>().eq("managers_id", managers.getId()));
            List<MerchantTax> merchantTaxes = merchantTaxDao.selectList(new QueryWrapper<MerchantTax>().eq("tax_id", tax.getId()));
            for (MerchantTax merchantTax : merchantTaxes) {
                merchantIds.add(merchantTax.getId());
            }
        } else {
            List<Merchant> merchants = merchantService.list();
            for (Merchant merchant : merchants) {
                merchantIds.add(merchant.getId());
            }
        }
        return merchantIds;
    }
}
