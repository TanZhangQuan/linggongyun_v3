package com.example.mybatis.mapper;

import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.vo.InvoiceDetailsVo;
import com.example.mybatis.vo.SubpackageInfoVo;
import com.example.mybatis.vo.SubpackageVo;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 *
 * 分包发票接口
 */
public interface SubpackageDao {
    //汇总代开
    List<SubpackageVo> getSummaryList(TobeinvoicedDto tobeinvoicedDto, RowBounds rowBounds);
    //根据发票id查询单个发票对应支付信息
    SubpackageVo getSummary(String invoiceId);

    //发票信息
    SubpackageInfoVo getSubpackageInfoById(String invoiceId);

    List<InvoiceDetailsVo> getListById(String invoiceId, RowBounds rowBounds);
}
