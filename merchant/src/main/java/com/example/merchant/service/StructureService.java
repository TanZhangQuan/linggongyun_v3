package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.AgentInfoDto;
import com.example.merchant.dto.ManagersDto;
import com.example.merchant.exception.CommonException;

public interface StructureService {
    ReturnJson addSalesMan(ManagersDto managersDto);

    ReturnJson updateSalesMan(ManagersDto managersDto);

    ReturnJson findBySalesManId(String managersId);

    ReturnJson getSalesManAll(Integer page, Integer pageSize);

    ReturnJson removeSalesMan(String salesManId) throws CommonException;

    ReturnJson getSalesManPaymentListCount(String salesManId) throws CommonException;

    ReturnJson getSalesManPaymentList(String salesManId, Integer page, Integer pageSize) throws CommonException;

    ReturnJson getSalesManPaymentInfo(String paymentOrderId, Integer packageStatus);

    ReturnJson getPaymentInventory(String paymentOrderId, Integer page, Integer pageSize);

    ReturnJson addAgent(AgentInfoDto agentInfoDto);

    ReturnJson updataAgent(AgentInfoDto agentInfoDto);

    ReturnJson getAgentAll(Integer page, Integer pageSize);

    ReturnJson findByAgentId(String agentId);

    ReturnJson removeAgent(String agentId);

}
