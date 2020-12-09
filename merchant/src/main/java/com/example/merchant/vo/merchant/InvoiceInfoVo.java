package com.example.merchant.vo.merchant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/8
 */
@Data
public class InvoiceInfoVo {
    /**
     * 发票Url
     */
    @ApiModelProperty("发票Url")
    private String invoiceUrl;

    /**
     * 税票Url
     */
    @ApiModelProperty("税票Url")
    private String taxReceiptUrl;
}
