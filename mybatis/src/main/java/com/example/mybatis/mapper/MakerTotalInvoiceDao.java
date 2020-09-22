package com.example.mybatis.mapper;

import com.example.mybatis.entity.MakerTotalInvoice;

public interface MakerTotalInvoiceDao {
    int deleteByPrimaryKey(String id);

    int insert(MakerTotalInvoice record);

    int insertSelective(MakerTotalInvoice record);

    MakerTotalInvoice selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MakerTotalInvoice record);

    int updateByPrimaryKey(MakerTotalInvoice record);
}