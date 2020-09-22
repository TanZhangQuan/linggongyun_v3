package com.example.mybatis.mapper;

import com.example.mybatis.entity.Tax;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.po.TaxPO;
import com.example.mybatis.vo.SellerVo;

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
    //销售方查询
    SellerVo getSellerById(String id);

    List<TaxPO> selectByMerchantId(String merchantId);
}
