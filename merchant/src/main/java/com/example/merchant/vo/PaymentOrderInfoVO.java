package com.example.merchant.vo;

import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.po.PaymentOrderInfoPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "支付订单详情")
public class PaymentOrderInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付订单信息")
    private PaymentOrderInfoPO paymentOrderInfoPO;

    @ApiModelProperty(value = "支付明细")
    private List<PaymentInventory> paymentInventories;

    @ApiModelProperty(value = "总包或众包发票")
    private String invoice;

    @ApiModelProperty(value = "分包发票，当订单为众包时分包发票为空")
    private String SubpackageInvoice;

    @ApiModelProperty(value = "发票的快递信息")
    private ExpressInfoVO expressInfoVO;

}
