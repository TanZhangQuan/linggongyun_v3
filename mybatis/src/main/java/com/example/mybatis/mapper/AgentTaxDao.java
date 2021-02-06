package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.entity.AgentTax;

/**
 * @author .
 * @date 2021/1/29.
 * @time 15:29.
 */
public interface AgentTaxDao  extends BaseMapper<AgentTax> {


    /**
     * 根据代理商和服务商加合作状态查询
     */
    AgentTax getAgentTax(String taxId,String agentId,Integer chargeStatus);


}
