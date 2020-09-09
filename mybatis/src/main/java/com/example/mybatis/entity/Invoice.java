package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 发票相关
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_invoice")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;


    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * 商户名称
     */
    private String merchantsName;

    /**
     * 支付编号
     */
    private String payCode;

    /**
     * 支付金额
     */
    private BigDecimal invoiceMoney;

    /**
     * 支付清单
     */
    private String payDetailedAccount;

    /**
     * 总包支付回单
     */
    private String gcPayReceipt;

    /**
     * 众包支付回单
     */
    private String cPaymentReceipt;

    /**
     * 总包发票
     */
    private String gcInvoice;

    /**
     * 众包发票
     */
    private String cInvoice;

    /**
     * 0总包+分包，1众包
     */
    private Integer packageStatus;

    /**
     * 状态
     */
    private Integer state;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 用户修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

}
