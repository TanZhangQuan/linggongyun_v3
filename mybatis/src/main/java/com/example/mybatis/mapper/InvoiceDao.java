package com.example.mybatis.mapper;

import com.example.mybatis.entity.Invoice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.po.InvoicePO;

import java.util.List;

/**
 * <p>
 * 总包发票 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-21
 */
public interface InvoiceDao extends BaseMapper<Invoice> {
    InvoicePO selectInvoiceMoney(String merchantId);
    InvoicePO selectInvoiceMoneyPaas(List<String> merchantId);
}
