package com.example.mybatis.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * tb_maker_invoice
 * @author 
 */
@Data
@TableName("tb_maker_invoice")
public class MakerInvoice implements Serializable {
    /**
     * 门征单开主键
     */
    @TableId(type = IdType.ASSIGN_ID,value = "id")
    private String id;

    /**
     * 创客支付ID
     */
    private String paymentInventoryId;

    /**
     * 发票代码
     */
    private String invoiceTypeNo;

    /**
     * 发票号码
     */
    private String invoiceSerialNo;

    /**
     * 发票开具日期
     */
    private LocalDateTime makerVoiceGetDateTime;

    /**
     * 服务名称
     */
    private String invoiceCategory;

    /**
     * 开票金额
     */
    private BigDecimal totalAmount;

    /**
     * 税额合计
     */
    private BigDecimal taxAmount;

    /**
     * 开票人
     */
    private String ivoicePerson;

    /**
     * 销售方名称
     */
    private String saleCompany;

    /**
     * 代开机关名称
     */
    private String helpMakeOrganationName;

    /**
     * 代开企业名称
     */
    private String helpMakeCompany;

    /**
     * 代开企业税号
     */
    @ApiModelProperty(value = "代开企业税号")
    private String helpMakeTaxNo;

    /**
     * 发票URL
     */
    private String makerVoiceUrl;

    /**
     * 税票URL
     */
    private String makerTaxUrl;

    /**
     * 发票上传日期
     */
    private LocalDateTime makerVoiceUploadDateTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}