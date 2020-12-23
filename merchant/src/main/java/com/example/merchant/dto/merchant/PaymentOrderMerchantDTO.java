package com.example.merchant.dto.merchant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "查询订单参数")
public class PaymentOrderMerchantDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付订单ID")
    private String paymentOrderId;

    @ApiModelProperty(value = "服务平台ID")
    private String taxId;

    @ApiModelProperty(value = "页数")
    @NotNull(message = "页数不能为空")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "一页多少条数据")
    @NotNull(message = "每页的条数不能为空")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "开始时间")
    private String beginDate;

    @ApiModelProperty(value = "结束时间")
    private String endDate;
}
