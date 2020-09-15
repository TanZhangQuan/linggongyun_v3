package com.example.paas.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.paas.service.AgentService;
import com.example.mybatis.entity.Agent;
import com.example.mybatis.mapper.AgentDao;
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
