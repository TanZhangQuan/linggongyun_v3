package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.dto.QuerySubpackageDTO;
import com.example.mybatis.vo.InvoiceDetailsVO;
import com.example.mybatis.vo.PaymentOrderVO;
import com.example.mybatis.vo.SubpackageInfoVO;
import com.example.mybatis.vo.SubpackageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分包发票接口
 */
public interface SubpackageDao {
    //汇总代开
    IPage<SubpackageVO> getSummaryList(Page page, QuerySubpackageDTO querySubpackageDto, String merchantId);

    //根据发票id查询单个发票对应支付信息
    SubpackageVO getSummary(String invoiceId);

    //发票信息
    SubpackageInfoVO getSubpackageInfoById(String invoiceId);

    IPage<InvoiceDetailsVO> getListById(Page page, @Param("invoiceId") String invoiceId);

    List<PaymentOrderVO> queryPaymentOrderInfo(String invoiceId);
}
