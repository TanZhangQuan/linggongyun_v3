package com.example.merchant.vo.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/10
 */
@Data
public class CrowdSourcingInvoiceVo {

    @ApiModelProperty(value = "修改时用")
    private String id;

    @ApiModelProperty(value = "发票数字")
    private String invoiceNumber;

    @ApiModelProperty(value = "发票代码")
    private String invoiceCodeno;

    @ApiModelProperty(value = "发票存放url")
    private String invoiceUrl;

    @ApiModelProperty(value = "税票存放url")
    private String taxReceiptUrl;

    @ApiModelProperty(value = "开票说明")
    private String invoiceDesc;
}
