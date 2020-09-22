package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 总包发票
 * </p>
 *
 * @author hzp
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_invoice")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 申请开票id
     */
    private String applicationId;

    /**
     * 发票编号，FP+0000
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
     * 开票人(销售方)
     */
    private String invoicePrintPerson;

    /**
     * 申请开票人(购买方)
     */
    private String applicationInvoicePerson;

    /**
     * 发票张数
     */
    private Integer invoiceNumbers;

    /**
     * 发票金额
     */
    private BigDecimal invoiceMoney;

    /**
     * 开票类目
     */
    private String invoiceCatalogId;

    /**
     * 发票地址
     */
    private String invoiceUrl;

    /**
     * 税票地址
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
