package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.entity.AgentLadderService;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author jun.
 * @date 2021/1/29.
 * @time 15:30.
 */
public interface AgentLadderServiceDao extends BaseMapper<AgentLadderService> {

    BigDecimal queryServiceCharge(@Param("agentTaxId") String companyTaxId, @Param("startMoney") BigDecimal startMoney, @Param("endMoney") BigDecimal endMoney);

    AgentLadderService queryAgentLadderService(@Param("agentTaxId") String companyTaxId, @Param("startMoney") BigDecimal startMoney, @Param("endMoney") BigDecimal endMoney);
}
