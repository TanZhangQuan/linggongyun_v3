package com.example.merchant.vo.merchant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "发票申请")
public class InvoiceApplicationVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "申请开票ID")
    private String id;

    @ApiModelProperty(value = "开票类目")
    private String invoiceCatalogType;

    @ApiModelProperty(value = "申请说明")
    private String applicationDesc;

    @ApiModelProperty(value = "地址")
    private String applicationAddress;
}
