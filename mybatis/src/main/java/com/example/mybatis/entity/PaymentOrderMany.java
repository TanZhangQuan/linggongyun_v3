package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
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
@EqualsAndHashCode(callSuper = false)
@TableName("tb_payment_order_many")
public class PaymentOrderMany implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付订单ID
     */
    @ApiModelProperty(notes = "众包订单ID", value = "众包订单ID", hidden = true)

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 商户的公司ID
     */
    @ApiModelProperty(notes = "商户的公司ID", value = "商户的公司ID", required = true)
    private String companyId;

    /**
     * 商户的公司简称
     */
    @ApiModelProperty(notes = "商户的公司简称", value = "商户的公司简称" ,required = true)
    private String companySName;

    /**
     * 支付金额
     */
    @ApiModelProperty(notes = "支付金额", value = "支付金额", required = true)
    private BigDecimal realMoney;

    /**
     * 平台服务商ID
     */
    @ApiModelProperty(notes = "平台服务商ID", value = "平台服务商ID" ,required = true)
    private String taxId;
    /**
     * 平台服务商
     */
    @ApiModelProperty(notes = "平台服务商", value = "平台服务商")
    private String platformServiceProvider;

    /**
     * 项目合同（存储位置）
     */
    @ApiModelProperty(notes = "项目合同（存储位置）", value = "项目合同（存储位置）",required = true)
    private String companyContract;

    /**
     * 支付清单（存储位置）
     */
    @ApiModelProperty(notes = "支付清单（存储位置）", value = "支付清单（存储位置）",required = true)
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
     * 支付订单的状态
     */
    @ApiModelProperty(notes = "支付订单的状态", value = "支付订单的状态")
    private Integer paymentOrderStatus;

    /**
     * 支付时间
     */
    @ApiModelProperty(notes = "支付时间", value = "支付时间", hidden = true)
    private LocalDateTime paymentDate;

    /**
     * 支付订单的创建时间
     */
    @ApiModelProperty(notes = "支付订单的创建时间", value = "支付订单的创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 支付订单的修改时间
     */
    @ApiModelProperty(notes = "支付订单的修改时间", value = "支付订单的修改时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;


}
