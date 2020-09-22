package com.example.mybatis.vo;

import lombok.Data;

/**
 * 众包发票已开票
 */
@Data
public class CrowdSourcingInfoVo {

    //发票id
    private String id;
    //商户ID
    private String merchantId;
    //发票code
    private String invoiceCode;
    //商户名称
    private String companySName;
    //服务商名称
    private String platformServiceProvider;
    //众包支付id
    private String pomId;
    //支付清单Url
    private String paymentInventory;
    //众包支付回单
    private String manyPayment;
    //是否申请开票
    private Integer isApplication;
    //发票URL
    private String invoiceUrl;
    //税票URL
    private String taxReceiptUrl;
    //申请开票状态0.未申请；1.申请中；2.已拒绝；3.已开票，4未开票'
    private Integer applicationState;
    //开票时间
    private String invoicePrintDate;

}
