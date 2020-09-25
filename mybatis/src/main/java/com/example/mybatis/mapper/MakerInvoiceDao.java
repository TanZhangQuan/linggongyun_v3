package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.entity.MakerInvoice;
import com.example.mybatis.vo.InvoiceListVo;

import java.util.List;

public interface MakerInvoiceDao extends BaseMapper<MakerInvoice> {

    List<InvoiceListVo> getInvoiceListQuery(String InvoiceId);
}