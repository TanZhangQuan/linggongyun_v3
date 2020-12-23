package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class ToSubcontractInvoiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总包发票ID")
    private String id;

    @ApiModelProperty(value = "商户名称")
    private String companySName;

    @ApiModelProperty(value = "服务商名称")
    private String platformServiceProvider;

    @ApiModelProperty(value = "总包发票")
    private String invoiceUrl;

    @ApiModelProperty(value = "总包税票")
    private String taxReceiptUrl;

    @ApiModelProperty(value = "是否开票，0未开票(待开票)，1已开票")
    private String isSubpackage;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime paymentDate;

    @ApiModelProperty(value = "对应支付清单可能有多个")
    private List<OrderSubpackageVO> list;
}
