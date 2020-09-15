package com.example.paas.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.paas.service.CompanyInfoService;
import com.example.mybatis.entity.CompanyInfo;
import com.example.mybatis.mapper.CompanyInfoDao;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公司信息
 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class CompanyInfoServiceImpl extends ServiceImpl<CompanyInfoDao, CompanyInfo> implements CompanyInfoService {

}
