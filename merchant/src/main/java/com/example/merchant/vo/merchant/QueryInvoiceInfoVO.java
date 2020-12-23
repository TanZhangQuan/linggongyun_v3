package com.example.merchant.vo.merchant;

import com.example.common.util.ExpressLogisticsInfo;
import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.PaymentOrderManyVO;
import com.example.mybatis.vo.SendAndReceiveVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class QueryInvoiceInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付信息")
    private PaymentOrderManyVO paymentOrderManyVo;

    @ApiModelProperty(value = "销售方信息")
    private BuyerVO buyerVo;

    @ApiModelProperty(value = "申请开票信息")
    private InvoiceApplicationVO invoiceApplicationVo;

    @ApiModelProperty(value = "开票类目")
    private InvoiceCatalogVO invoiceCatalogVo;

    @ApiModelProperty(value = "发票信息")
    private InvoiceVO invoiceVo;

    @ApiModelProperty(value = "发收件信息")
    private SendAndReceiveVO sendAndReceiveVo;

    @ApiModelProperty(value = "物流信息")
    private List<ExpressLogisticsInfo> expressLogisticsInfoList;
}
