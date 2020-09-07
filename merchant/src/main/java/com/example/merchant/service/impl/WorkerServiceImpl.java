package com.example.merchant.service.impl;

import com.example.mybatis.entity.Worker;
import com.example.mybatis.mapper.WorkerDao;
import com.example.merchant.service.WorkerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 创客表 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class WorkerServiceImpl extends ServiceImpl<WorkerDao, Worker> implements WorkerService {

}
