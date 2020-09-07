package com.example.merchant.service.impl;

import com.example.mybatis.entity.Tax;
import com.example.mybatis.mapper.TaxDao;
import com.example.merchant.service.TaxService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 合作园区信息 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class TaxServiceImpl extends ServiceImpl<TaxDao, Tax> implements TaxService {

}
