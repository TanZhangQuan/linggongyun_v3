package com.example.merchant.vo.merchant;

import com.example.common.util.ExpressLogisticsInfo;
import com.example.mybatis.vo.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class QueryInvoiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付信息")
    private List<PaymentOrderVO> paymentOrderVoList;

    @ApiModelProperty(value = "开票信息")
    private List<BillingInfoVO> billingInfoVoList;

    @ApiModelProperty(value = "购买方信息")
    private BuyerVO buyerVo;

    @ApiModelProperty(value = "销售方信息")
    private SellerVO sellerVo;

    @ApiModelProperty(value = "开票类目")
    private InvoiceCatalogVO invoiceCatalogVo;

    @ApiModelProperty(value = "发票信息")
    private InvoiceApplicationVO invoiceApplicationVo;

    @ApiModelProperty(value = "物流信息")
    private List<ExpressLogisticsInfo> expressLogisticsInfoList;

    @ApiModelProperty(value = "收发件信息")
    private SendAndReceiveVO sendAndReceiveVo;

    @ApiModelProperty(value = "发票")
    private String invoiceUrl;

    @ApiModelProperty(value = "税票")
    private String taxReceiptUrl;

}
