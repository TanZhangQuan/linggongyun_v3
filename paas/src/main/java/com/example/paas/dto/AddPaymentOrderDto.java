package com.example.paas.dto;

import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.entity.PaymentOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("创建订单的参数")
public class AddPaymentOrderDto implements Serializable {
    @ApiModelProperty("支付清单")
    private List<PaymentInventory> paymentInventories;
    @ApiModelProperty("支付订单")
    private PaymentOrder paymentOrder;
}
