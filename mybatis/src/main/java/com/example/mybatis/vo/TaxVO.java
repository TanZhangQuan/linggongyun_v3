package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(value = "监管服务商列表")
public class TaxVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商ID
     */
    private String id;

    /**
     * 服务商名称
     */
    private String taxName;

    /**
     * 总包支付单数
     */
    private Integer paymentOrderNum;

    /**
     * 总包交易流水
     */
    private BigDecimal paymentOrderMoney;

    /**
     * 支付单数
     */
    private String paymentOrderCount;

    /**
     * 众包支付单数
     */
    private Integer paymentOrderManyNum;

    /**
     * 总包交易流水
     */
    private BigDecimal paymentOrderManyMoney;

    /**
     * 状态
     */
    private Integer taxStatus;

    /**
     * 前端使用状态
     */
    private String status;

    /**
     * 入驻时间
     */
    private String createDate;
}
