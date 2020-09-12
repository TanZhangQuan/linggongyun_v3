package com.example.mybatis.mapper;

import com.example.mybatis.entity.Merchant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 商户信息
 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface MerchantDao extends BaseMapper<Merchant> {

    Merchant findByID(String id);

    List<Merchant> getIdAndName();

    String getNameById(String id);
}
