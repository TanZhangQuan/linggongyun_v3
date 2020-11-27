package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * tb_maker_total_invoice
 * @author 
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_maker_total_invoice")
public class MakerTotalInvoice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 申请开票id
     */
    private String invoiceApplicationId;

    /**
     * 发票代码
     */
    private String invoiceTypeNo;

    /**
     * 发票号码
     */
    private String invoiceSerialNo;

    /**
     * 开票日期
     */
    private LocalDateTime invoiceDate;

    /**
     * 服务类型
     */
    private String invoiceCategory;

    /**
     * 价税合计
     */
    private BigDecimal totalAmount;

    /**
     * 税额总价
     */
    private BigDecimal taxAmount;

    /**
     * 开票人
     */
    private String invoicePerson;

    /**
     * 销售方名称
     */
    private String saleCompany;

    /**
     * 开票说明
     */
    private String makerInvoiceDesc;

    /**
     * 分包发票url
     */
    private String makerInvoiceUrl;

    /**
     * 分包完税证明URL
     */
    private String makerTaxUrl;

    /**
     * 发票上传日期
     */
    private LocalDateTime makerVoiceUploadDateTime;

}