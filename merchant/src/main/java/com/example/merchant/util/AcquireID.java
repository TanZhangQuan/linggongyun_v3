package com.example.merchant.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.util.VerificationCheck;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.CompanyInfoService;
import com.example.mybatis.entity.CompanyInfo;
import com.example.mybatis.entity.Managers;
import com.example.mybatis.mapper.ManagersDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取当前管理人员的所以商户ID
 */
@Component
public class AcquireID {

    @Resource
    private ManagersDao managersDao;

    @Resource
    private CompanyInfoService companyInfoService;

    public List<String> getCompanyIds(String managersId) throws CommonException {
        List<String> companyIds = new ArrayList<>();
        Managers managers = managersDao.selectById(managersId);
        if (managers == null) {
            throw new CommonException(300, "输入的ID有误，没有这个管理人员存在！");
        }
        Integer userSign = managers.getUserSign();
        //管理人员为代理商
        if (userSign == 1) {
            List<CompanyInfo> merchants = companyInfoService.list(new QueryWrapper<CompanyInfo>().eq("agent_id", managers.getId()));
            for (CompanyInfo merchant : merchants) {
                companyIds.add(merchant.getId());
            }
            //管理人员为业务员
        } else if (userSign == 2) {
            List<CompanyInfo> merchants = companyInfoService.list(new QueryWrapper<CompanyInfo>().eq("sales_man_id", managers.getId()));
            for (CompanyInfo merchant : merchants) {
                companyIds.add(merchant.getId());
            }
        } else {
            List<CompanyInfo> merchants = companyInfoService.list();
            for (CompanyInfo merchant : merchants) {
                companyIds.add(merchant.getId());
            }
        }
        if (VerificationCheck.listIsNull(companyIds)) {
            throw new CommonException(200, "该管理人员没有关联的商户！");
        }
        return companyIds;
    }
}
