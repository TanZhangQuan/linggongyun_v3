package com.example.merchant.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.CompanyInfoService;
import com.example.mybatis.entity.CompanyInfo;
import com.example.mybatis.entity.Managers;
import com.example.mybatis.mapper.ManagersDao;
import com.example.mybatis.mapper.MerchantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取当前管理人员的所以商户ID
 */
@Component
public class AcquireID {

    @Autowired
    private ManagersDao managersDao;

    @Autowired
    private CompanyInfoService companyInfoService;

    @Autowired
    private MerchantDao merchantDao;

    public List<String> getMerchantIds(String managersId) throws CommonException {
        List<String> merchantIds = new ArrayList<>();
        Managers managers = managersDao.selectById(managersId);
        if (managers == null) {
            throw new CommonException(300, "输入的ID有误，没有这个管理人员存在！");
        }
        Integer userSign = managers.getUserSign();
        if (userSign == 1) {//管理人员为代理商
            List<CompanyInfo> merchants = companyInfoService.list(new QueryWrapper<CompanyInfo>().eq("agent_id", managers.getId()));
            for (CompanyInfo merchant : merchants) {
                merchantIds.add(merchant.getId());
            }
        } else if (userSign == 2) {//管理人员为业务员
            List<CompanyInfo> merchants = companyInfoService.list(new QueryWrapper<CompanyInfo>().eq("sales_man_id", managers.getId()));
            for (CompanyInfo merchant : merchants) {
                merchantIds.add(merchant.getId());
            }
        } else {
            List<CompanyInfo> merchants = companyInfoService.list();
            for (CompanyInfo merchant : merchants) {
                merchantIds.add(merchant.getId());
            }
        }
        return merchantIds;
    }
}
