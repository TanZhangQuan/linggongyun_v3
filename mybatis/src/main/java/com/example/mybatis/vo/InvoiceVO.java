package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class InvoiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 发票ID
     */
    private String id;

    /**
     * 申请开票ID
     */
    private String applicationId;

    /**
     * 发票ID
     */
    private String invoiceCode;

    /**
     * 商户名称
     */
    private String companySName;

    /**
     * 服务商名称
     */
    private String platformServiceProvider;

    /**
     * 开票申请说明
     */
    private String applicationDesc;

    /**
     * 申请状态
     */
    private Integer applicationState;

    /**
     * 申请状态
     */
    private String invoicePrintDate;

    /**
     * 总包发票
     */
    private String invoiceUrl;

    /**
     * 总包税票
     */
    private String taxReceiptUrl;

    /**
     * 商户是否申请
     */
    private String isInvoice;

    /**
     * 总包支付信息
     */
    private List<OrderSubpackageVO> list;
}
