package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author .
 * @date 2021/1/12.
 * @time 10:11.
 */
@Data
@ApiModel(description = "支付明细")
public class PaymentInventoryVO implements Serializable {

    @ApiModelProperty("支付单ID")
    private String paymentOrderId;

    @ApiModelProperty("创客ID")
    private String workerId;

    @ApiModelProperty("创客姓名")
    private String workerName;

    @ApiModelProperty("创客电话")
    private String mobileCode;

    @ApiModelProperty("创客身份证号码")
    private String idCardCode;

    @ApiModelProperty("银行名称")
    private String bankName;

    @ApiModelProperty("创客的银行账号")
    private String bankCode;

    @ApiModelProperty("任务金额")
    private BigDecimal taskMoney;

    @ApiModelProperty("创客的实际到手的金额")
    private BigDecimal realMoney;

    @ApiModelProperty("服务费")
    private BigDecimal serviceMoney;

    @ApiModelProperty("综合税率")
    private BigDecimal compositeTax;

    @ApiModelProperty("纳税率")
    private BigDecimal taxRate;

    @ApiModelProperty("纳税金额")
    private BigDecimal taxAmount;

    @ApiModelProperty("商户支付金额")
    private BigDecimal merchantPaymentMoney;

    @ApiModelProperty("实名认证状态(0未认证，1已认证)")
    private Integer attestation;

    @ApiModelProperty("支付状态(-1支付失败，0未支付，1已支付)")
    private Integer paymentStatus;

    @ApiModelProperty("交易失败原因")
    private String tradeFailReason;

    @ApiModelProperty("合作类型0总包，1众包")
    private Integer packageStatus;

    @ApiModelProperty("商户承担的税率的百分比（如50，不需要把百分比换算成小数）")
    private BigDecimal merchantTax;

    @ApiModelProperty("创客承担的税率的百分比（如50，不需要把百分比换算成小数）")
    private BigDecimal receviceTax;

    private String id;

    @ApiModelProperty("用户创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;

    @ApiModelProperty("用户修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateDate;

    public void setMerchantTax(BigDecimal merchantTax) {
        this.merchantTax = this.compositeTax.multiply(merchantTax).divide(new BigDecimal("100"),2);
    }

    public void setReceviceTax(BigDecimal receviceTax) {
        this.receviceTax = this.compositeTax.multiply(receviceTax).divide(new BigDecimal("100"),2);
    }
}
