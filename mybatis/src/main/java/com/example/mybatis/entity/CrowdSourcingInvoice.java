package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
@ApiModel
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
    @ApiModelProperty("申请id:可以为空，为空则说明商户未申请")
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
    @Pattern(regexp = "^[0-9]*$",message = "必须为数字")
    @ApiModelProperty("发票数字")
    @NotNull(message = "发票数字不能为空")
    private String invoiceNumber;

    /**
     * 发票代码
     */
    @ApiModelProperty("发票代码")
    @Pattern(regexp = "^[0-9]*$",message = "必须为数字")
    @NotNull(message = "发票代码不能为空")
    @TableField("invoice_codeNo")
    private String invoiceCodeno;

    /**
     * 购买方
     */
    @NotNull(message = "购买方不能为空")
    @ApiModelProperty("购买方")
    private String invoicePrintPerson;

    /**
     * 开票金额
     */
    @ApiModelProperty("开票金额")
    @NotNull(message = "开票金额不能为空")
    private BigDecimal invoiceMoney;

    /**
     * 开票类目
     */
    @ApiModelProperty("开票类目")
    @NotNull(message = "开票类目不能为空")
    private String invoiceCatalogId;

    /**
     * 发票Url
     */
    @ApiModelProperty("发票Url")
    @NotNull(message = "发票Url不能为空")
    private String invoiceUrl;

    /**
     * 税票Url
     */
    @ApiModelProperty("税票Url")
    @NotNull(message = "税票Url不能为空")
    private String taxReceiptUrl;

    /**
     * 快递单号
     */
    @ApiModelProperty("快递单号")
    @NotNull(message = "快递单号不能为空")
    private String expressSheetNo;

    /**
     * 快递公司
     */
    @ApiModelProperty("快递公司")
    @NotNull(message = "快递公司不能为空")
    private String expressCompanyName;

    /**
     * 快递更新时间
     */
    @ApiModelProperty("快递更新时间")
    private LocalDateTime expressUpdateDatetime;

    /**
     * 快递更新人员
     */
    @ApiModelProperty("快递更新人员")
    private String expressUpdatePerson;

    /**
     * 快递更新人员电话
     */
    @ApiModelProperty("快递更新人员电话")
    private String expressUpdatePersonTel;

    /**
     * 开票说明
     */
    @ApiModelProperty("开票说明")
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
