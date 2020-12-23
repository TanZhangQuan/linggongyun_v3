package com.example.merchant.vo.platform;

import com.example.merchant.vo.merchant.InvoiceCatalogVO;
import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.PaymentOrderVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class QueryMakerTotalInvoiceDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付信息")
    private List<PaymentOrderVO> paymentOrderVOList;

    @ApiModelProperty(value = "购买信息")
    private BuyerVO queryBuyer;

    @ApiModelProperty(value = "汇总代开发票信息")
    private MakerTotalInvoiceDetailsVO makerTotalInvoiceDetails;

    @ApiModelProperty(value = "开票类目信息")
    private InvoiceCatalogVO invoiceCatalogVo;
}
