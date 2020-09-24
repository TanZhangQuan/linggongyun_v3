package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.entity.MakerInvoice;
import com.example.mybatis.vo.InvoiceListVo;

import java.util.List;

public interface MakerInvoiceDao extends BaseMapper<MakerInvoice> {
    int deleteByPrimaryKey(String id);

    int insert(MakerInvoice record);

    int insertSelective(MakerInvoice record);

    MakerInvoice selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MakerInvoice record);

    int updateByPrimaryKey(MakerInvoice record);

    List<InvoiceListVo> getInvoiceListQuery(String InvoiceId);
}