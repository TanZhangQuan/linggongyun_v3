package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "众包开票明细")
public class InvoiceDetailsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付清单ID")
    private String id;

    @ApiModelProperty(value = "创客ID")
    private String workerId;

    @ApiModelProperty(value = "创客名称")
    private String workerName;

    @ApiModelProperty(value = "创客电话号码")
    private String mobileCode;

    @ApiModelProperty(value = "创客身份证")
    private String idCardCode;

    @ApiModelProperty(value = "创客银行卡号")
    private String bankCode;

    @ApiModelProperty(value = "创客到手费用")
    private String realMoney;

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

}
