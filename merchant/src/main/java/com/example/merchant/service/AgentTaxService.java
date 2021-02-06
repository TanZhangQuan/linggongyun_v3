package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.AgentTax;
import com.example.mybatis.vo.CompanyPackageDetailVO;

import java.util.List;

/**
 * @author jun.
 * @date 2021/1/29.
 * @time 15:32.
 */
public interface AgentTaxService extends IService<AgentTax> {

    ReturnJson queryAgentPackage(String taxId, String agentId, Integer packageStatus);

    AgentTax queryAgentTax(String taxId, String agentId);

}
