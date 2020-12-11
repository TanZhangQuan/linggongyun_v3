package com.example.mybatis.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/11
 */
@Data
public class MakerTotalInvoiceVo {
    /**
     * 汇总代开发票ID
     */
    private String id;
    /**
     * 公司ID
     */
    private String companyId;
    /**
     * 公司名称
     */
    private String companySName;
    /**
     * 服务商ID
     */
    private String taxId;
    /**
     * 服务商名称
     */
    private String platformServiceProvider;
    /**
     * 总包发票URL
     */
    private String invoiceUrl;
    /**
     * 总包税票URL
     */
    private String taxReceiptUrl;
    /**
     * 分包发票URl
     */
    private String makerInvoiceUrl;
    /**
     * 分包税票URL
     */
    private String makerTaxUrl;
    /**
     * 是否开票
     */
    private String isSubpackage;
    /**
     * 开票时间
     */
    private String createDate;
    /**
     * 支付信息
     */
    private List<PayVo> payVoList;
}
