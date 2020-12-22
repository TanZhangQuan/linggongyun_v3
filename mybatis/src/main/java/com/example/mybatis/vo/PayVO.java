package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/8
 */
@Data
public class PayVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付ID
     */
    private String pId;

    /**
     * 支付清单
     */
    private String paymentTnventory;

    /**
     * 支付回单
     */
    private String subpackagePayment;
}
