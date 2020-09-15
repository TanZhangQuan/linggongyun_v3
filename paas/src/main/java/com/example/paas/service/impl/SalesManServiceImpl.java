package com.example.paas.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.paas.service.SalesManService;
import com.example.mybatis.entity.SalesMan;
import com.example.mybatis.mapper.SalesManDao;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务员信息 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class SalesManServiceImpl extends ServiceImpl<SalesManDao, SalesMan> implements SalesManService {

}
