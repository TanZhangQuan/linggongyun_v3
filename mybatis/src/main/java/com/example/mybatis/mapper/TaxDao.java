package com.example.mybatis.mapper;

import com.example.mybatis.entity.Tax;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.po.TaxPO;

import java.util.List;

/**
 * <p>
 * 服务商公司信息
 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-21
 */
public interface TaxDao extends BaseMapper<Tax> {

    List<TaxPO> selectByMerchantId(String merchantId);
}
