package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 分包发票,待开票
 */
@Data
public class ToSubcontractInvoiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *总包发票id
     */
    private String id;

    /**
     * 商户名称
     */
    private String companySName;

    /**
     * 服务商名称
     */
    private String platformServiceProvider;

    /**
     * 总包发票
     */
    private String invoiceUrl;

    /**
     * 总包税票
     */
    private String taxReceiptUrl;

    /**
     * 是否开票，0未开票(待开票)，1已开票
     */
    private String isSubpackage;

    /**
     * 支付时间
     */
    private LocalDateTime paymentDate;

    /**
     * 对应支付清单可能有多个
     */
    private List<OrderSubpackageVO> list;
}
