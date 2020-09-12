package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Merchant;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    ReturnJson getIdAndName();

    String getNameById(String id);
}
