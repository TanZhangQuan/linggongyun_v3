package com.example.merchant.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PaymentOrderDto {
    @NotBlank(message = "商户ID不能为空")
    private String merchantId;

    /**
     * 支付订单ID
     */
    private String paymentOrderId;

    /**
     * 服务平台ID
     */
    private String taxId;

    /**
     * 页数
     */
    @NotNull(message = "页数不能为空")
    private Integer page = 1;

    /**
     * 一页多少条数据
     */
    @NotNull(message = "每页的条数不能为空")
    private Integer pageSize = 10;

    /**
     * 开始时间
     */
    private String beginDate;

    /**
     * 结束时间
     */
    private String endDate;
}
