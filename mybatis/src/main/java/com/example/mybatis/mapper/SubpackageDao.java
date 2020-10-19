package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.vo.InvoiceDetailsVo;
import com.example.mybatis.vo.SubpackageInfoVo;
import com.example.mybatis.vo.SubpackageVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 *
 * 分包发票接口
 */
public interface SubpackageDao {
    //汇总代开
    IPage<SubpackageVo> getSummaryList(Page page,@Param("tobeinvoicedDto") TobeinvoicedDto tobeinvoicedDto);
    //根据发票id查询单个发票对应支付信息
    SubpackageVo getSummary(String invoiceId);

    //发票信息
    SubpackageInfoVo getSubpackageInfoById(String invoiceId);

    IPage<InvoiceDetailsVo> getListById(Page page, @Param("invoiceId") String invoiceId);
}
