package com.example.merchant.vo.merchant;

import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.PaymentOrderVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class QuerySubInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付信息")
    private List<PaymentOrderVO> paymentOrderVos;

    @ApiModelProperty(value = "销售方信息")
    private BuyerVO buyerVo;

    @ApiModelProperty(value = "开票类目信息")
    private InvoiceCatalogVO invoiceCatalogVo;

    @ApiModelProperty(value = "发票信息")
    private InvoiceInfoVO invoiceInfoVo;

    @ApiModelProperty(value = "备注说明")
    private String remarks;
}
