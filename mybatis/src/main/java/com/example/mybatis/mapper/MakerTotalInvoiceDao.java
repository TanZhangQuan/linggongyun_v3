package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.dto.QueryMakerTotalInvoiceDto;
import com.example.mybatis.entity.MakerTotalInvoice;
import com.example.mybatis.vo.MakerTotalInvoiceVO;

public interface MakerTotalInvoiceDao extends BaseMapper<MakerTotalInvoice> {

    IPage<MakerTotalInvoiceVO> queryMakerTotalInvoice(Page page, QueryMakerTotalInvoiceDto queryMakerTotalInvoiceDto);
}