package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "查询订单参数")
public class PaymentOrderDto {

    /**
     * 管理人员ID
     */
    @ApiModelProperty("管理人员ID")
    private String managersId;

    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 支付订单ID
     */
    @ApiModelProperty("支付订单ID")
    private String paymentOrderId;

    /**
     * 服务平台ID
     */
    @ApiModelProperty("服务平台ID")
    private String taxId;

    /**
     * 页数
     */
    @ApiModelProperty("页数")
    @NotNull(message = "页数不能为空")
    private Integer page = 1;

    /**
     * 一页多少条数据
     */
    @ApiModelProperty("一页多少条数据")
    @NotNull(message = "每页的条数不能为空")
    private Integer pageSize = 10;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private String beginDate;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private String endDate;
}
