package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.entity.Agent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.po.AgentListPO;

/**
 * <p>
 * 代理商信息
 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface AgentDao extends BaseMapper<Agent> {
    IPage<AgentListPO> selectAgentList(Page page);
}
