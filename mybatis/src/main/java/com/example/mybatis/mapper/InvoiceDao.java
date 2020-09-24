package com.example.mybatis.mapper;

import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.Invoice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.po.InvoicePO;
import com.example.mybatis.vo.*;
import org.apache.ibatis.session.RowBounds;

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

    List<TobeinvoicedVo> selectTobeinvoiced(TobeinvoicedDto tobeinvoicedDto, RowBounds rowBounds);

    List<InvoiceVo> getInvoiceList(TobeinvoicedDto tobeinvoicedDto, RowBounds rowBounds);

    List<InvoiceListVo> getInvoiceListQuery(List<String> InvoiceIds);

    InvoicePO selectInvoiceMoneyPaasTax(String taxId);

    /**
     * 根据发票id查询发票信息
     * @return
     */
    InvoiceInformationVo getInvInfoById(String invId);

    List<InvoiceVo> getPlaInvoiceList(TobeinvoicedDto tobeinvoicedDto,RowBounds rowBounds);

    //更具申请id查询信息
    PlaInvoiceInfoVo getPlaInvoiceInfo(String applicationId);

    String getInvoiceCode();

    List<InvoiceVo> getListInvoicequery(TobeinvoicedDto tobeinvoicedDto,RowBounds rowBounds);

    //分包待开票数据
    List<ToSubcontractInvoiceVo> getListSubQuery(TobeinvoicedDto tobeinvoicedDto, RowBounds rowBounds);
}
