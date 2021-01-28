package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.merchant.service.TaxWorkerService;
import com.example.mybatis.entity.TaxWorker;
import com.example.mybatis.mapper.TaxWorkerDao;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商户信息 服务实现类
 * </p>
 *
 * @author tzq
 * @since 2021-01-27
 */
@Service
public class TaxWorkerServiceImpl extends ServiceImpl<TaxWorkerDao, TaxWorker> implements TaxWorkerService {

}
