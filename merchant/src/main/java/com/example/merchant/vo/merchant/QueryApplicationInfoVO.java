package com.example.merchant.vo.merchant;

import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.PaymentOrderManyVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class QueryApplicationInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付信息")
    private PaymentOrderManyVO paymentOrderManyVo;

    @ApiModelProperty(value = "销售方信息")
    private BuyerVO buyerVo;

    @ApiModelProperty(value = "申请开票信息")
    private InvoiceApplicationVO invoiceApplicationVo;
}
