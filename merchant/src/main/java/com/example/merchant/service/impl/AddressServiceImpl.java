package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.entity.Address;
import com.example.mybatis.mapper.AddressDao;
import com.example.merchant.service.AddressService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地址表 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressDao, Address> implements AddressService {

}
