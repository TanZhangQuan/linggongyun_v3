package com.example.merchant.service.impl;

import com.example.mybatis.entity.Merchant;
import com.example.mybatis.mapper.MerchantDao;
import com.example.merchant.service.MerchantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商户信息
 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantDao, Merchant> implements MerchantService {

}
