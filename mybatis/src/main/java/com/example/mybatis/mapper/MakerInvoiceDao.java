package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.entity.MakerInvoice;
import com.example.mybatis.vo.InvoiceListVO;

import java.util.List;

public interface MakerInvoiceDao extends BaseMapper<MakerInvoice> {

    List<InvoiceListVO> getInvoiceListQuery(String InvoiceId);
}