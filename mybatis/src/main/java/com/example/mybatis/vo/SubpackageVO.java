package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class SubpackageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发票ID")
    private String id;

    @ApiModelProperty(value = "发票编号")
    private String invoiceCode;

    @ApiModelProperty(value = "商户名称")
    private String companySName;

    @ApiModelProperty(value = "服务商名称")
    private String platformServiceProvider;

    @ApiModelProperty(value = "总包发票")
    private String invoiceUrl;

    @ApiModelProperty(value = "总包税票")
    private String taxReceiptUrl;

    @ApiModelProperty(value = "分包发票")
    private String makerInvoiceUrl;

    @ApiModelProperty(value = "分包税票")
    private String makerTaxUrl;

    @ApiModelProperty(value = "是否申请开分包")
    private Integer isSubpackage;

    @ApiModelProperty(value = "开票时间")
    private String invoiceDate;

    @ApiModelProperty(value = "支付状态")
    private Integer paymentOrderStatus;

    @ApiModelProperty(value = "支付时间")
    private String paymentDate;

    @ApiModelProperty(value = "税价合计")
    private Double totalAmount;

    @ApiModelProperty(value = "对应的支付清单")
    private List<OrderSubpackageVO> list;

    @ApiModelProperty(value = "关联的任务")
    private List<TaskVO> taskVOList;
}
