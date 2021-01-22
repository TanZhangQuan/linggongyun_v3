package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 众包支付单信息
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
@TableName("tb_payment_order_many")
public class PaymentOrderMany extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商户的公司ID
     */
    @ApiModelProperty(notes = "商户的公司ID", value = "商户的公司ID", required = true)
    private String companyId;

    /**
     * 商户的公司简称
     */
    @ApiModelProperty(notes = "商户的公司简称", value = "商户的公司简称", required = true)
    private String companySName;

    /**
     * 支付金额
     */
    @ApiModelProperty(notes = "支付金额", value = "支付金额", required = true)
    private BigDecimal realMoney;

    /**
     * 总服务费
     */
    private BigDecimal serviceMoney;

    /**
     * 平台服务商ID
     */
    @ApiModelProperty(notes = "平台服务商ID", value = "平台服务商ID", required = true)
    private String taxId;
    /**
     * 平台服务商
     */
    @ApiModelProperty(notes = "平台服务商", value = "平台服务商")
    private String platformServiceProvider;

    /**
     * 项目合同（存储位置）
     */
    @ApiModelProperty(notes = "项目合同（存储位置）", value = "项目合同（存储位置）", required = true)
    private String companyContract;

    /**
     * 支付清单（存储位置）
     */
    @ApiModelProperty(notes = "支付清单（存储位置）", value = "支付清单（存储位置）", required = true)
    private String paymentInventory;

    /**
     * 众包支付回单（存储位置）
     */
    @ApiModelProperty(notes = "众包支付回单（存储位置）", value = "众包支付回单（存储位置）")
    private String manyPayment;

    /**
     * 关联的任务(可以不关联)
     */
    @ApiModelProperty(notes = "关联的任务", value = "关联的任务")
    private String taskId;

    /**
     * 任务名称
     */
    @ApiModelProperty(notes = "任务名称", value = "任务名称")
    private String taskName;

    /**
     * 支付验收单（存储位置）
     */
    @ApiModelProperty(notes = "支付验收单", value = "支付验收单")
    private String acceptanceCertificate;

    /**
     * 0商户承担，1创客承担，2商户创客共同承担
     */
    @ApiModelProperty(notes = "税率承担：0商户承担，1创客承担，2商户创客共同承担", value = "税率承担：0商户承担，1创客承担，2商户创客共同承担")
    private Integer taxStatus;

    /**
     * 综合税率(综合税率=商户承担的税率+创客承担的税率)
     */
    @ApiModelProperty(notes = "综合税率", value = "综合税率")
    private BigDecimal compositeTax;

    /**
     * 商户承担的税率
     */
    @ApiModelProperty(notes = "商户承担的税率百分比", value = "商户承担的税率百分比")
    private BigDecimal merchantTax;

    /**
     * 创客承担的税率
     */
    @ApiModelProperty(notes = "创客承担的税率百分比", value = "创客承担的税率百分比")
    private BigDecimal receviceTax;

    /**
     * 支付订单的状态 -1支付失败 0申请中，1待支付，2支付中，3已完成，4已取消
     */
    @ApiModelProperty(notes = "支付订单的状态", value = "支付订单的状态")
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
     * 是否申请开票
     */
    private Integer isApplication;

    /**
     * 是否开票
     */
    private Integer isNotInvoice;

    /**
     * 支付时间
     */
    @ApiModelProperty(notes = "支付时间", value = "支付时间", hidden = true)
    private LocalDateTime paymentDate;

    /**
     * 订单号
     */
    private String tradeNo;

}
