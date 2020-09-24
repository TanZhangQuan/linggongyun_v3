package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.entity.MakerTotalInvoice;

public interface MakerTotalInvoiceDao extends BaseMapper<MakerTotalInvoice> {
    int deleteByPrimaryKey(String id);

    int insert(MakerTotalInvoice record);

    int insertSelective(MakerTotalInvoice record);

    MakerTotalInvoice selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MakerTotalInvoice record);

    int updateByPrimaryKey(MakerTotalInvoice record);
}