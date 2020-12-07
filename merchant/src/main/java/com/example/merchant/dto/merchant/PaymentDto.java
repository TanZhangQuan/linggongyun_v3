package com.example.merchant.dto.merchant;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Description 总包支付信息
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/11/30
 */
@Data
public class PaymentDto {

    private String id;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务类型（行业类型）
     */
    private String taskType;

    /**
     * 商户的公司简称
     */
    @ApiParam(hidden = true)
    private String companySName;

    /**
     * 平台服务商ID
     */
    @NotBlank(message = "平台服务商ID不能为空")
    private String taxId;

    /**
     * 项目合同（存储位置）
     */
    @NotBlank(message = "平台服务商不能为空")
    private String companyContract;

    /**
     * 支付清单（存储位置）
     */
    @NotBlank(message = "平台服务商不能为空")
    private String paymentInventory;

    /**
     * 关联的任务(可以不关联)
     */
    private String taskId;

    /**
     * 支付验收单（存储位置）
     */
    @NotBlank(message = "平台服务商不能为空")
    private String acceptanceCertificate;

    /**
     * 0商户承担，1创客承担，2商户创客共同承担
     */
    @NotNull(message = "请选择服务费率承担者")
    private Integer taxStatus;

    /**
     * 支付订单的状态
     */
    @ApiParam(hidden = true)
    private Integer paymentOrderStatus = 0;

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
}
