package com.example.paas.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.entity.Managers;
import com.example.mybatis.mapper.ManagersDao;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-15
 */
@Service
public class ManagersServiceImpl extends ServiceImpl<ManagersDao, Managers> implements IService<Managers> {

}
