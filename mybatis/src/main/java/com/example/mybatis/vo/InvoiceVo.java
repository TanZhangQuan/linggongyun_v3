package com.example.mybatis.vo;

import lombok.Data;

import java.util.List;

@Data
public class InvoiceVo {

    //发票id
    private String id;

    //申请开票id
    private String applicationId;

    //发票编号
    private String invoiceCode;

    //商户名称
    private String companySName;

    //服务商名称
    private String platformServiceProvider;

    //开票申请说明
    private String applicationDesc;

    //申请状态
    private Integer applicationState;

    //申请时间
    private String invoicePrintDate;

    //总包发票
    private String invoiceUrl;

    //总包税票
    private String taxReceiptUrl;

    //商户是否申请
    private String isInvoice;

    //总包支付信息
    private List<OrderSubpackageVo> list;
}
