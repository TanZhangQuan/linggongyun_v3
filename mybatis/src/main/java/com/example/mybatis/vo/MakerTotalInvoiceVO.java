package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class MakerTotalInvoiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "汇总代开发票ID")
    private String id;

    @ApiModelProperty(value = "公司ID")
    private String companyId;

    @ApiModelProperty(value = "公司名称")
    private String companySName;

    @ApiModelProperty(value = "服务商ID")
    private String taxId;

    @ApiModelProperty(value = "服务商名称")
    private String platformServiceProvider;

    @ApiModelProperty(value = "总包发票URL")
    private String invoiceUrl;

    @ApiModelProperty(value = "总包税票URL")
    private String taxReceiptUrl;

    @ApiModelProperty(value = "分包发票URl")
    private String makerInvoiceUrl;

    @ApiModelProperty(value = "分包税票URL")
    private String makerTaxUrl;

    @ApiModelProperty(value = "是否开票")
    private String isSubpackage;

    @ApiModelProperty(value = "开票时间")
    private String createDate;

    @ApiModelProperty(value = "支付信息")
    private List<PayVO> payVOList;
}
