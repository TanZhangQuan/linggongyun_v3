package com.example.merchant.dto.merchant;

import com.example.mybatis.entity.PaymentInventory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel(description = "添加总包订单")
public class AddPaymentOrderDto {

    @ApiModelProperty(notes = "总包订单内容", value = "总包订单内容")
    private PaymentDto paymentDto;

    @ApiModelProperty(notes = "支付清单订单内容", value = "支付清单订单内容")
    private List<PaymentInventory> paymentInventories;
}
