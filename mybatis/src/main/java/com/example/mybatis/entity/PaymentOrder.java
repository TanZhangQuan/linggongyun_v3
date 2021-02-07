package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 支付单信息
 *
 * </p>
 *
 * @author hzp
 * @since 2020-09-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_payment_order")
public class PaymentOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商户的公司ID
     */
    private String companyId;

    /**
     * 商户的公司简称
     */
    private String companySName;

    /**
     * 商家支付的金额
     */
    private BigDecimal realMoney;

    /**
     * 总服务费
     */
    private BigDecimal serviceMoney;

    /**
     * 付给创客的金额
     */
    private BigDecimal workerMoney;

    /**
     * 平台服务商ID
     */
    private String taxId;

    /**
     * 平台服务商
     */
    private String platformServiceProvider;

    /**
     * 项目合同（存储位置）
     */
    private String companyContract;

    /**
     * 支付清单（存储位置）
     */
    private String paymentInventory;

    /**
     * 总包支付回单（存储位置）
     */
    private String turnkeyProjectPayment;

    /**
     * 分包支付回单（存储位置）
     */
    private String subpackagePayment;

    /**
     * 是否申请总包开票：0为申请,1已申请
     */
    private Integer isInvoice;

    /**
     * 是否分包开票0，为开票 1，已开票
     */
    private Integer isSubpackage;

    /**
     * 是否开票 0 已开， 1 未开
     */
    private Integer isNotInvoice;

    /**
     * 关联的任务(可以不关联)
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 支付验收单（存储位置）
     */
    private String acceptanceCertificate;

    /**
     * 0商户承担，1创客承担，2商户创客共同承担
     */
    private Integer taxStatus;

    /**
     * 综合税率(综合税率=商户承担的税率+创客承担的税率)
     */
    private BigDecimal compositeTax;

    /**
     * 商户承担的税率
     */
    private BigDecimal merchantTax;

    /**
     * 创客承担的税率
     */
    private BigDecimal receviceTax;

    /**
     * 支付订单的状态 -1支付失败, 0申请中，1待支付，2已支付，3已确认收款, 4支付中（可能支付失败，用来避免重复支付）5已驳回 6已完成
     */
    private Integer paymentOrderStatus;

    /**
     * 驳回理由
     */
    private String reasonsForRejection;

    /**
     * 交易失败原因
     */
    private String tradeFailReason;

    /**
     * 支付方式：0线下支付,1连连支付,2网商银行支付,3银联盛京银行,4银联平安银行,5银联网商银行,6银联招商银行
     */
    private Integer paymentMode;

    /**
     * 支付人的ID
     */
    private String merchantId;

    /**
     * 支付时间
     */
    private LocalDateTime paymentDate;

    /**
     * 订单号
     */
    private String tradeNo;

    /**
     *代理商差额佣金
     */
//    private BigDecimal totalAgentDifference;

    /**
     *业务员差额佣金
     */
//    private BigDecimal totalSalesmanDifference;

}
