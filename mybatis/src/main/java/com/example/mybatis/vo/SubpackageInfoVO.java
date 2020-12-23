package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class SubpackageInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "开票类目")
    private String invoiceCategory;

    @ApiModelProperty(value = "备注说明")
    private String makerInvoiceDesc;

    @ApiModelProperty(value = "发票地址")
    private String makerInvoiceUrl;

    @ApiModelProperty(value = "税票地址")
    private String makerTaxUrl;
}
