package com.example.mybatis.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 众包发票待开票视图
 */
@Data
public class CrowdSourcingInvoiceInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 申请ID
     */
    private String applicationId;

    /**
     * 商户ID
     */
    private String companyId;

    /**
     * 商户名称
     */
    private String companySName;

    /**
     * 服务商名称
     */
    private String platformServiceProvider;

    /**
     * 众包支付ID
     */
    private String id;

    /**
     * 众包支付清单
     */
    private String paymentInventory;

    /**
     * 众包支付回单
     */
    private String manyPayment;
    /**
     * 开票状态为空则为未申请
     */
    private String isInvoice;

    /**
     * 0.未申请；1.申请中；2.已拒绝；3.已开票，4未开票
     */
    private String isApplication;

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date applicationDate;

}
