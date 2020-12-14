package com.example.merchant.vo.platform;

import lombok.Data;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/11
 */
@Data
public class MakerTotalInvoiceDetailsVo {

    /**
     * 汇总代开发票ID
     */
    private String id;

    /**
     * 备注
     */
    private String makerInvoiceDesc;

    /**
     * 分包发票url
     */
    private String makerInvoiceUrl;

    /**
     * 分包完税证明URL
     */
    private String makerTaxUrl;

    /**
     * 发票代码
     */
    private String invoiceTypeNo;

    /**
     * 发票号码
     */
    private String invoiceSerialNo;
}
