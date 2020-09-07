package com.example.merchant.service.impl;

import com.example.mybatis.entity.SalesMan;
import com.example.mybatis.mapper.SalesManDao;
import com.example.merchant.service.SalesManService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
