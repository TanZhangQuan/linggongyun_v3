package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatis.entity.AgentLadderService;

import java.math.BigDecimal;

/**
 * @author jun.
 * @date 2021/1/29.
 * @time 15:33.
 */
public interface AgentLadderServiceService  extends IService<AgentLadderService> {
    /**
     * 获取某梯度价税率
     *
     * @param agentTaxId
     * @param startMoney
     * @param endMoney
     * @return
     */
    BigDecimal queryServiceCharge(String agentTaxId, BigDecimal startMoney, BigDecimal endMoney);
}
