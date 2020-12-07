package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 众包发票已开票
 */
@Data
public class CrowdSourcingInfoVo {

    //发票id
    private String id;
    //商户ID
    private String companyId;
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
    //开票状态已开票，未开票
    private String applicationState;
    //开票时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime invoicePrintDate;

}
