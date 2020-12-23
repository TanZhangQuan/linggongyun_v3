package com.example.merchant.dto.merchant;

import com.example.merchant.dto.platform.PaymentOrderManyDTO;
import com.example.mybatis.entity.PaymentInventory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "添加众包订单")
public class AddPaymentOrderManyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "众包订单内容")
    @NotNull(message = "众包订单内容不能为空")
    private PaymentOrderManyDTO paymentOrderManyDto;

    @ApiModelProperty(value = "支付清单订单内容")
    @NotEmpty(message = "支付清单订单内容不能为空")
    private List<PaymentInventory> paymentInventories;
}
