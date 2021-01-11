package com.example.merchant.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class MakerTotalInvoiceDetailsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "汇总代开发票ID")
    private String id;

    @ApiModelProperty(value = "备注")
    private String makerInvoiceDesc;

    @ApiModelProperty(value = "分包发票")
    private String makerInvoiceUrl;

    @ApiModelProperty(value = "分包完税证明")
    private String makerTaxUrl;

}
