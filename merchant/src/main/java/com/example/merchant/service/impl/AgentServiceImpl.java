package com.example.merchant.service.impl;

import com.example.mybatis.entity.Agent;
import com.example.mybatis.mapper.AgentDao;
import com.example.merchant.service.AgentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 代理商信息
 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class AgentServiceImpl extends ServiceImpl<AgentDao, Agent> implements AgentService {

}
