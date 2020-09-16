package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.entity.Invoice;
import com.example.mybatis.po.InvoicePO;

import java.util.List;

/**
 * <p>
 * 发票相关 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface InvoiceDao extends BaseMapper<Invoice> {
    List<InvoicePO> selectTotal(String merchantId);
    List<InvoicePO> selectTotalpaas(List<String> merchantId);
}
