package com.example.merchant.vo.merchant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/7
 */
@Data
public class InvoiceVo {

    /**
     * 发票Url
     */
    @ApiModelProperty("发票Url")
    @NotNull(message = "发票Url不能为空")
    private String invoiceUrl;

    /**
     * 税票Url
     */
    @ApiModelProperty("税票Url")
    @NotNull(message = "税票Url不能为空")
    private String taxReceiptUrl;

    /**
     * 快递单号
     */
    @ApiModelProperty("快递单号")
    @NotNull(message = "快递单号不能为空")
    private String expressSheetNo;

    /**
     * 快递公司
     */
    @ApiModelProperty("快递公司")
    @NotNull(message = "快递公司不能为空")
    private String expressCompanyName;
}
