package com.example.merchant.vo.merchant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class InvoiceInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发票")
    private String invoiceUrl;

    @ApiModelProperty(value = "税票")
    private String taxReceiptUrl;
}
