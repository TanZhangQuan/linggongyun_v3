package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.AgentInfoDTO;
import com.example.merchant.dto.platform.ManagersDTO;
import com.example.merchant.exception.CommonException;

public interface StructureService {
    /**
     * 添加或修改
     *
     * @param managersDto
     * @return
     */
    ReturnJson addSalesMan(ManagersDTO managersDto);

    /**
     * 按ID查找业务员
     *
     * @param managersId
     * @return
     */
    ReturnJson findBySalesManId(String managersId);

    /**
     * 分页查询所有业务员
     * @param page
     * @param pageSize
     * @return
     */
    ReturnJson getSalesManAll(Integer page, Integer pageSize);

    /**
     * 删除业务员
     *
     * @param salesManId
     * @return
     */
    ReturnJson removeSalesMan(String salesManId) throws CommonException;

    /**
     * 业务员的流水统计
     *
     * @param salesManId
     * @return
     */
    ReturnJson getSalesManPaymentListCount(String salesManId) throws CommonException;

    /**
     * 业务员的流水
     *
     * @param salesManId
     * @param page
     * @param pageSize
     * @return
     */
    ReturnJson getSalesManPaymentList(String salesManId, Integer page, Integer pageSize) throws CommonException;

    /**
     * 查询支付订单详情
     *
     * @param paymentOrderId
     * @param packageStatus
     * @return
     */
    ReturnJson getSalesManPaymentInfo(String paymentOrderId, Integer packageStatus);

    /**
     * 获取支付清单列表
     *
     * @param paymentOrderId
     * @param page
     * @param pageSize
     * @return
     */
    ReturnJson getPaymentInventory(String paymentOrderId, Integer page, Integer pageSize);

    /**
     * 添加代理商
     *
     * @param agentInfoDto
     * @return
     */
    ReturnJson addAgent(AgentInfoDTO agentInfoDto) throws Exception;


    /**
     * 查询所以代理商
     *
     * @param page
     * @param pageSize
     * @return
     */
    ReturnJson getAgentAll(Integer page, Integer pageSize);

    /**
     * 按ID查找代理商(编辑代理商时用来获取代理商信息)
     *
     * @param agentId
     * @return
     */
    ReturnJson findByAgentId(String agentId);

    /**
     * 删除没有业务的代理商
     *
     * @param agentId
     * @return
     */
    ReturnJson removeAgent(String agentId);

    /**
     * 查询所有的代理员
     *
     * @return
     */
    ReturnJson querySalesman(String userId);

    /**
     * 平台业务员和代理商佣金保存
     */
    void timerStatistics();

    /**
     * 平台业务和代理商佣金统计
     * @param userId
     * @param time
     * @param pageNo
     * @param pageSize
     * @return
     */
    ReturnJson salesmanAndAgentStatistics(String userId,Integer objectType,String time,Integer pageNo,Integer pageSize);

    /**
     * 平台业务员和代理商总佣金记录
     */
    ReturnJson totalSalesmanAndAgentStatistics(String userId,Integer objectType);

    /**
     * 平台业务员或代理商佣金记录详情
     */
    ReturnJson salesmanSAndAgentStatisticsDetail(String managersId,String achievementStatisticsId);

    /**
     *平台业务员或代理商总包众包订单详情
     */
    ReturnJson salesmanSAndAgentDetail(String managersId,Integer customerType,Integer pageNo,Integer pageSize);
}
