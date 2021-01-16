package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.MD5;
import com.example.merchant.service.CompanyInfoService;
import com.example.mybatis.entity.CompanyInfo;
import com.example.mybatis.mapper.CompanyInfoDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公司信息
 * 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class CompanyInfoServiceImpl extends ServiceImpl<CompanyInfoDao, CompanyInfo> implements CompanyInfoService {

    @Value("${PWD_KEY}")
    String PWD_KEY;

    @Override
    public boolean verifyPayPwd(String companyId, String payPassWord) {

        CompanyInfo companyInfo = getById(companyId);
        if (companyInfo == null) {
            return false;
        }

        return (PWD_KEY + MD5.md5(payPassWord)).equals(companyInfo.getPayPwd());
    }

}
