package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatis.entity.CompanyLadderService;

import java.math.BigDecimal;

public interface CompanyLadderServiceService extends IService<CompanyLadderService> {

    /**
     * 获取某梯度价税率
     *
     * @param companyTaxId
     * @param startMoney
     * @param endMoney
     * @return
     */
    BigDecimal queryServiceCharge(String companyTaxId, BigDecimal startMoney, BigDecimal endMoney);
}
