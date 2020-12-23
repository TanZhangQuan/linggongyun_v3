package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "众包开票详情页支付信息")
public class PaymentOrderManyVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付ID")
    private String id;

    @ApiModelProperty(value = "商户名称")
    private String companySName;

    @ApiModelProperty(value = "服务商名称")
    private String platformServiceProvider;

    @ApiModelProperty(value = "支付清单")
    private String paymentInventory;

    @ApiModelProperty(value = "交付支付验收单")
    private String acceptanceCertificate;

    @ApiModelProperty(value = "支付回单")
    private String manyPayment;

    @ApiModelProperty(value = "支付状态")
    private Integer paymentOrderStatus;

    @ApiModelProperty(value = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime paymentDate;

    @ApiModelProperty(value = "价税总和")
    private BigDecimal realMoney;

    @ApiModelProperty(value = "关联任务")
    private TaskVO taskVo;
}
