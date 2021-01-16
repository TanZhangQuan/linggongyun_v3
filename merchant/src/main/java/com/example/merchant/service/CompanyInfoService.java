package com.example.merchant.service;

import com.example.mybatis.entity.CompanyInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 公司信息
 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface CompanyInfoService extends IService<CompanyInfo> {

    /**
     * 校验支付密码
     *
     * @param companyId
     * @param payPassWord
     * @return
     */
    boolean verifyPayPwd(String companyId, String payPassWord);
}
