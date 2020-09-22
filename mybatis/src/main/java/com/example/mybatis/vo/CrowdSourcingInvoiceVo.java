package com.example.mybatis.vo;


import lombok.Data;

/**
 * 众包发票待开票视图
 */
@Data
public class CrowdSourcingInvoiceVo {

    private String companySName;

    private String platformServiceProvider;

    private String id;

    private String paymentInventory;

    private String manyPayment;

    /**
     * 申请时间
     */
    private String applicationDate;

    /**
     * 是否申请开票0，未申请1，已开启
     */
    private String isApplication;
}
