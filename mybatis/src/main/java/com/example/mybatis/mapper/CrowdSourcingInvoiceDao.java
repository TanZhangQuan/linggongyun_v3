package com.example.mybatis.mapper;

import com.example.mybatis.entity.CrowdSourcingInvoice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.po.InvoicePO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-21
 */
public interface CrowdSourcingInvoiceDao extends BaseMapper<CrowdSourcingInvoice> {
    InvoicePO selectCrowdInvoiceMoney(String merchantId);
    InvoicePO selectCrowdInvoiceMoneyPaas(List<String> merchantId);
}
