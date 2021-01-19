package com.example.mybatis.vo;

import com.example.common.enums.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "交易记录")
public class PaymentHistoryListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "交易记录ID")
    private String id;

    @ApiModelProperty(value = "交易订单号")
    private String tradeNo;

    @ApiModelProperty(value = "第三方订单号")
    private String outerTradeNo;

    @ApiModelProperty(value = "交易类型")
    private OrderType orderType;

    @ApiModelProperty(value = "交易方式")
    private PaymentMethod paymentMethod;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "交易结果")
    private TradeStatus tradeStatus;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

}
