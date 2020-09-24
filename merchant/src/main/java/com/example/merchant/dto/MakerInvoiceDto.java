package com.example.merchant.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MakerInvoiceDto {

    /**
     * 门征单开主键
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 创客支付ID
     */
    @NotNull(message = "创客支付ID，不能为空")
    @ApiModelProperty(value = "创客支付ID")
    private String paymentInventoryId;

    /**
     * 发票代码
     */
    @NotNull(message = "发票代码不能为空")
    @ApiModelProperty(value = "发票代码")
    private String invoiceTypeNo;

    /**
     * 发票号码
     */
    @NotNull(message = "发票号码不能为空")
    @ApiModelProperty(value = "发票号码")
    private String invoiceSerialNo;

    /**
     * 发票开具日期
     */
    @ApiModelProperty(value = "发票开具日期")
    private LocalDateTime makerVoiceGetDateTime;

    /**
     * 服务名称
     */
    @NotNull(message = "服务名称不能为空")
    @ApiModelProperty(value = "服务名称,获取总包的服务类型")
    private String invoiceCategory;

    /**
     * 价税合计
     */
    @NotNull(message = "开票金额不能为空")
    @ApiModelProperty(value = "开票金额,任务金额")
    private BigDecimal totalAmount;

    /**
     * 税额合计
     */
    @NotNull(message = "税额合计不能为空")
    @ApiModelProperty(value = "税额合计,纳税金额")
    private BigDecimal taxAmount;

    /**
     * 开票人
     */
    @NotNull(message = "开票人不能为空")
    @ApiModelProperty(value = "开票人,购买方")
    private String ivoicePerson;

    /**
     * 销售方名称
     */
    @NotNull(message = "销售方名称不能为空")
    @ApiModelProperty(value = "销售方名称")
    private String saleCompany;

    /**
     * 代开机关名称
     */
    @NotNull(message = "代开机关名称不能为空")
    @ApiModelProperty(value = "代开机关名称")
    private String helpMakeOrganationName;

    /**
     * 代开企业名称
     */
    @NotNull(message = "代开企业名称不能为空")
    @ApiModelProperty(value = "代开企业名称")
    private String helpMakeCompany;

    /**
     * 代开企业税号
     */
    @NotNull(message = "代开企业税号不能为空")
    @ApiModelProperty(value = "代开企业税号")
    private String helpMakeTaxNo;

    /**
     * 发票URL
     */
    @ApiModelProperty(value = "发票URL")
    private String makerVoiceUrl;

    /**
     * 税票URL
     */
    @ApiModelProperty(value = "税票URL")
    private String makerTaxUrl;

    /**
     * 发票上传日期
     */
    @ApiModelProperty(value = "发票上传日期")
    private LocalDateTime makerVoiceUploadDateTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
