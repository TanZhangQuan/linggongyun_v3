package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author hzp
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_crowd_sourcing_invoice")
public class CrowdSourcingInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 众包开票主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 申请id
     */
    private String applicationId;

    /**
     * 开票代码KP+0000
     */
    private String invoiceCode;

    /**
     * 开票时间
     */
    private LocalDateTime invoicePrintDate;

    /**
     * 发票数字
     */
    private String invoiceNumber;

    /**
     * 发票代码
     */
    @TableField("invoice_codeNo")
    private String invoiceCodeno;

    /**
     * 购买方
     */
    private String invoicePrintPerson;

    /**
     * 开票金额
     */
    private BigDecimal invoiceMoney;

    /**
     * 开票类目
     */
    private String invoiceCatalogId;

    /**
     * 发票Url
     */
    private String invoiceUrl;

    /**
     * 税票Url
     */
    private String taxReceiptUrl;

    /**
     * 快递单号
     */
    private String expressSheetNo;

    /**
     * 快递公司
     */
    private String expressCompanyName;

    /**
     * 快递更新时间
     */
    private LocalDateTime expressUpdateDatetime;

    /**
     * 快递更新人员
     */
    private String expressUpdatePerson;

    /**
     * 快递更新人员电话
     */
    private String expressUpdatePersonTel;

    /**
     * 开票说明
     */
    private String invoiceDesc;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;



}
