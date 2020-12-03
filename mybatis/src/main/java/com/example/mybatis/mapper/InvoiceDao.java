package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.dto.QueryTobeinvoicedDto;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.Invoice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.po.InvoiceInfoPO;
import com.example.mybatis.po.InvoicePO;
import com.example.mybatis.vo.*;
import org.apache.ibatis.annotations.Param;
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

    IPage<TobeinvoicedVo> selectTobeinvoiced(Page page, QueryTobeinvoicedDto queryTobeinvoicedDto,String companyId);

    IPage<InvoiceVo> getInvoiceList(Page page,QueryTobeinvoicedDto queryTobeinvoicedDto,String companyId);

    List<InvoiceListVo> getInvoiceListQuery(List<String> InvoiceIds);

    InvoicePO selectInvoiceMoneyPaasTax(String taxId);

    InvoicePO selectInvoiceMoneyPaasRegultor(@Param("taxIds") List<String> taxIds);

    /**
     * 根据发票id查询发票信息
     * @return
     */
    InvoiceInformationVo getInvInfoById(String invId);

    IPage<InvoiceVo> getPlaInvoiceList(Page page,@Param("tobeinvoicedDto") TobeinvoicedDto tobeinvoicedDto);

    //更具申请id查询信息
    PlaInvoiceInfoVo getPlaInvoiceInfo(String applicationId);

    String getInvoiceCode();

    IPage<InvoiceVo> getListInvoicequery(Page page,@Param("tobeinvoicedDto") TobeinvoicedDto tobeinvoicedDto);

    //分包待开票数据
    IPage<ToSubcontractInvoiceVo> getListSubQuery(Page page,@Param("tobeinvoicedDto")TobeinvoicedDto tobeinvoicedDto);

    //根据支付订单ID查找发票信息
    InvoiceInfoPO selectInvoiceInfoPO(String paymentOrderId);
}
