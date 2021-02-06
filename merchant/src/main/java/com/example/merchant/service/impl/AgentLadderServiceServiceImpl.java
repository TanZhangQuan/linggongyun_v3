package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.merchant.service.AgentLadderServiceService;
import com.example.mybatis.entity.AgentLadderService;
import com.example.mybatis.mapper.AgentLadderServiceDao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author jun.
 * @date 2021/1/29.
 * @time 15:33.
 */
@Service
public class AgentLadderServiceServiceImpl extends ServiceImpl<AgentLadderServiceDao, AgentLadderService> implements AgentLadderServiceService {

    @Override
    public BigDecimal queryServiceCharge(String agentTaxId, BigDecimal startMoney, BigDecimal endMoney) {
        return baseMapper.queryServiceCharge(agentTaxId, startMoney, endMoney);
    }
}
