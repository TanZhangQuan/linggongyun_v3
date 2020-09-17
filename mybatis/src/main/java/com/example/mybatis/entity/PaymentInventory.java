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
 * 支付清单明细

 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_payment_inventory")
public class PaymentInventory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付清单ID
     */
    @ApiModelProperty("支付清单ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 支付单ID
     */
    @ApiModelProperty("支付单ID")
    private String paymentOrderId;

    /**
     * 创客ID
     */
    @ApiModelProperty("创客ID")
    private String workerId;

    /**
     * 创客姓名
     */
    @ApiModelProperty("创客姓名")
    private String workerName;

    /**
     * 创客电话
     */
    @ApiModelProperty("创客电话")
    private String mobileCode;

    /**
     * 创客身份证号码
     */
    @ApiModelProperty("创客身份证号码")
    private String idCardCode;

    /**
     * 银行名称
     */
    @ApiModelProperty("银行名称")
    private String bankName;
    /**
     * 创客的银行账号
     */
    @ApiModelProperty("创客的银行账号")
    private String bankCode;

    /**
     * 创客的实际到手的金额
     */
    @ApiModelProperty("创客的实际到手的金额")
    private BigDecimal realMoney;

    /**
     * 服务费
     */
    @ApiModelProperty("服务费")
    private BigDecimal serviceMoney;

    /**
     * 综合税率
     */
    @ApiModelProperty("综合税率")
    private BigDecimal compositeTax;

    /**
     * 商户支付金额
     */
    @ApiModelProperty("商户支付金额")
    private BigDecimal merchantPaymentMoney;

    /**
     * 实名认证状态(0未认证，1已认证)
     */
    @ApiModelProperty("实名认证状态(0未认证，1已认证)")
    private Integer attestation;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 用户修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

}
