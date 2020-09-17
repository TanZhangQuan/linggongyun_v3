package com.example.paas.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PaymentOrderDto {

    /**
     * 管理人员ID
     */
    @NotBlank(message = "管理人员ID不能为空")
    @ApiModelProperty("管理人员ID")
    private String managersId;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 商户ID集合
     */
    private List<String> merchantIds;

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
    @Min(value = 1,message = "page页数不能小于1")
    private Integer page = 1;

    /**
     * 一页多少条数据
     */
    @ApiModelProperty("一页多少条数据")
    @NotNull(message = "每页的条数不能为空")
    @Min(value = 1,message = "pageSize每页的条数不能小于1")
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
