package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.dto.QueryMakerTotalInvoiceDTO;
import com.example.mybatis.entity.MakerTotalInvoice;
import com.example.mybatis.vo.MakerTotalInvoiceVO;

public interface MakerTotalInvoiceDao extends BaseMapper<MakerTotalInvoice> {

    IPage<MakerTotalInvoiceVO> queryMakerTotalInvoice(Page page, QueryMakerTotalInvoiceDTO queryMakerTotalInvoiceDto,Integer userType,String userId);
}