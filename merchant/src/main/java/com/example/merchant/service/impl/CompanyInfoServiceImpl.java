package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.config.JwtConfig;
import com.example.common.util.MD5;
import com.example.merchant.service.CompanyInfoService;
import com.example.mybatis.entity.CompanyInfo;
import com.example.mybatis.mapper.CompanyInfoDao;
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

    @Override
    public boolean verifyPayPwd(String companyId, String payPassWord) {

        CompanyInfo companyInfo = getById(companyId);
        if (companyInfo == null) {
            return false;
        }

        return (MD5.md5(JwtConfig.getSecretKey() + payPassWord)).equals(companyInfo.getPayPwd());
    }

}
