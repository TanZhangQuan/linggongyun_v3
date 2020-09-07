package com.example.merchant.service;

import com.example.mybatis.entity.Merchant;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商户信息
 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface MerchantService extends IService<Merchant> {

    Merchant findByID(String id);
}
