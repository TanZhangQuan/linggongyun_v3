package com.example.merchant.vo;

import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.po.PaymentOrderInfoPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "支付订单详情")
public class PaymentOrderInfoVO {

    @ApiModelProperty("支付订单信息")
    private PaymentOrderInfoPO paymentOrderInfoPO;
    @ApiModelProperty("支付明细")
    private List<PaymentInventory> paymentInventories;
}
