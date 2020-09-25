package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.entity.RegulatorTax;
import com.example.mybatis.mapper.RegulatorTaxDao;
import com.example.merchant.service.RegulatorTaxService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 监管部门监管的服务商 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-25
 */
@Service
public class RegulatorTaxServiceImpl extends ServiceImpl<RegulatorTaxDao, RegulatorTax> implements RegulatorTaxService {

}
