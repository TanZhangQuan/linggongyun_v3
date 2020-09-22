package com.example.mybatis.mapper;

import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.ApplicationCrowdSourcing;
import com.example.mybatis.entity.CrowdSourcingInvoice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.po.InvoicePO;
import com.example.mybatis.vo.CrowdSourcingInfoVo;
import com.example.mybatis.vo.InvoiceInformationVo;
import org.apache.ibatis.session.RowBounds;

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
    int addCrowdSourcingInvoice(ApplicationCrowdSourcing applicationCrowdSourcing);

    List<CrowdSourcingInfoVo> getCrowdSourcingInfo(TobeinvoicedDto tobeinvoicedDto, RowBounds rowBounds);

    //众包发票id
    InvoiceInformationVo getInvoiceById(String csiId);

    InvoicePO selectCrowdInvoiceMoneyPaasTax(String taxId);
}
