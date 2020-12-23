package com.example.merchant.vo.platform;

import com.example.merchant.vo.merchant.InvoiceCatalogVO;
import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.PaymentOrderManyVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class QueryNotInvoicedVO implements Serializable {
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
}
