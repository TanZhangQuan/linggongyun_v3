package com.example.merchant.vo.platform;

import com.example.common.util.ExpressLogisticsInfo;
import com.example.merchant.vo.merchant.InvoiceCatalogVO;
import com.example.mybatis.vo.SendAndReceiveVO;
import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.PaymentOrderManyVO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/10
 */
@Data
public class QueryInvoicedVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 众包支付信息
     */
    private PaymentOrderManyVO paymentOrderManyVo;

    /**
     * 购买方
     */
    private BuyerVO buyerVo;

    /**
     * 开票类目
     */
    private InvoiceCatalogVO invoiceCatalogVo;

    /**
     * 地址信息
     */
    private AddressVO addressVo;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 税价总和
     */
    private BigDecimal totalTaxPrice;
    /**
     * 众包发票信息
     */
    private CrowdSourcingInvoiceVO crowdSourcingInvoiceVo;

    /**
     * 发收件信息
     */
    private SendAndReceiveVO sendAndReceiveVo;

    /**
     * 物流信息
     */
    private List<ExpressLogisticsInfo> expressLogisticsInfoList;
}
