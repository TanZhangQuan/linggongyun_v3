package com.example.mybatis.mapper;

import com.example.mybatis.entity.InvoiceList;

public interface InvoiceListDao {
    int deleteByPrimaryKey(String id);

    int insert(InvoiceList record);

    int insertSelective(InvoiceList record);

    InvoiceList selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InvoiceList record);

    int updateByPrimaryKey(InvoiceList record);
}