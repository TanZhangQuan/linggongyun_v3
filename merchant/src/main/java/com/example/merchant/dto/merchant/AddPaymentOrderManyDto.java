package com.example.merchant.dto.merchant;

import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.entity.PaymentOrderMany;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(description = "添加众包订单")
public class AddPaymentOrderManyDto {

    @NotNull(message = "众包订单内容不能为空！")
    @ApiModelProperty(notes = "众包订单内容", value = "众包订单内容")
    private PaymentOrderMany paymentOrderMany;

    @NotEmpty(message = "支付清单订单内容不能为空！")
    @ApiModelProperty(notes = "支付清单订单内容", value = "支付清单订单内容")
    private List<PaymentInventory> paymentInventories;
}
