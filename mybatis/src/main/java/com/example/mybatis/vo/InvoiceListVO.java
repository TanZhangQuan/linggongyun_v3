package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "分包开票清单明细信息")
public class InvoiceListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付清单ID")
    private String id;

    @ApiModelProperty(value = "创客ID")
    private String workerId;

    @ApiModelProperty(value = "创客名称")
    private String workerName;

    @ApiModelProperty(value = "创客电话")
    private String mobileCode;

    @ApiModelProperty(value = "身份证号")
    private String idCardCode;

    @ApiModelProperty(value = "银行卡号")
    private String bankCode;

    @ApiModelProperty(value = "创客到手实际金额")
    private BigDecimal realMoney;

    @ApiModelProperty(value = "任务金额")
    private BigDecimal taskMoney;

    @ApiModelProperty(value = "服务商ID")
    private String taxId;

    @ApiModelProperty(value = "创客承担税率")
    private BigDecimal receviceTax;

    @ApiModelProperty(value = "纳税金额")
    private BigDecimal taxAmount;

    @ApiModelProperty(value = "纳税率")
    private BigDecimal taxRate;

    @ApiModelProperty(value = "个人服务费")
    private BigDecimal personalServiceFee;

    @ApiModelProperty(value = "门征单开使用是否分包开票")
    private String invoiceStatu;

    @ApiModelProperty(value = "门征单开使用分包发票")
    private String makerVoiceUrl;

    @ApiModelProperty(value = "门征单开使用分包税票")
    private String makerTaxUrl;

}
