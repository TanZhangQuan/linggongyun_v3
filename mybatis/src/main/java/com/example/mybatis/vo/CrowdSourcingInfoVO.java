package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 众包发票已开票
 */
@Data
public class CrowdSourcingInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 发票ID
     */
    private String id;

    /**
     * 商户ID
     */
    private String companyId;

    /**
     * 发票code
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
     * 众包支付ID
     */
    private String pomId;

    /**
     * 支付清单
     */
    private String paymentInventory;

    /**
     * 众包支付回单
     */
    private String manyPayment;

    /**
     * 是否申请开票
     */
    private Integer isApplication;

    /**
     * 发票
     */
    private String invoiceUrl;

    /**
     * 税票
     */
    private String taxReceiptUrl;

    /**
     * 开票状态
     */
    private String applicationState;

    /**
     * 开票时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime invoicePrintDate;

}
