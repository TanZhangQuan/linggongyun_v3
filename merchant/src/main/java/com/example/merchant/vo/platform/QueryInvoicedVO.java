package com.example.merchant.vo.platform;

import com.example.common.util.ExpressLogisticsInfo;
import com.example.merchant.vo.merchant.InvoiceCatalogVO;
import com.example.mybatis.vo.SendAndReceiveVO;
import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.PaymentOrderManyVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(description = "发票信息")
public class QueryInvoicedVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "众包支付信息")
    private PaymentOrderManyVO paymentOrderManyVo;

    @ApiModelProperty(value = "购买方")
    private BuyerVO buyerVo;

    @ApiModelProperty(value = "开票类目")
    private InvoiceCatalogVO invoiceCatalogVo;

    @ApiModelProperty(value = "地址信息")
    private AddressVO addressVo;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "税价总和")
    private BigDecimal totalTaxPrice;

    @ApiModelProperty(value = "众包发票信息")
    private CrowdSourcingInvoiceVO crowdSourcingInvoiceVo;

    @ApiModelProperty(value = "发收件信息")
    private SendAndReceiveVO sendAndReceiveVo;

    @ApiModelProperty(value = "物流信息")
    private List<ExpressLogisticsInfo> expressLogisticsInfoList;

}
