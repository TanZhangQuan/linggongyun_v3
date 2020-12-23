package com.example.merchant.vo.platform;

import com.example.common.util.ExpressLogisticsInfo;
import com.example.merchant.vo.merchant.InvoiceCatalogVO;
import com.example.mybatis.vo.SendAndReceiveVO;
import com.example.mybatis.vo.BillingInfoVO;
import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.PaymentOrderVO;
import com.example.mybatis.vo.SellerVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class QueryPlaInvoiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付信息")
    private List<PaymentOrderVO> paymentOrderVOList;

    @ApiModelProperty(value = "开票信息")
    private List<BillingInfoVO> billingInfoVOList;

    @ApiModelProperty(value = "购买方信息")
    private BuyerVO buyerVo;

    @ApiModelProperty(value = "销售方信息")
    private SellerVO sellerVo;

    @ApiModelProperty(value = "开票类目")
    private InvoiceCatalogVO invoiceCatalogVo;

    @ApiModelProperty(value = "发票信息")
    private PlaInvoiceInfoVO plaInvoiceInfoVo;

    @ApiModelProperty(value = "地址信息")
    private AddressVO addressVo;

    @ApiModelProperty(value = "发收件信息")
    private SendAndReceiveVO sendAndReceiveVo;

    @ApiModelProperty(value = "物流信息")
    private List<ExpressLogisticsInfo> expressLogisticsInfoList;
}
