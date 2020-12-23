package com.example.merchant.dto.merchant;

import com.example.mybatis.entity.PaymentInventory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "添加总包订单")
public class AddPaymentOrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总包订单内容")
    private PaymentDTO paymentDto;

    @ApiModelProperty(value = "支付清单订单内容")
    private List<PaymentInventory> paymentInventories;
}
