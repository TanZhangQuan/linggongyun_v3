package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/4
 */
@Data
public class SendAndReceiveVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 物流公司
     */
    private String logisticsCompany;

    /**
     * 物流单号
     */
    private String logisticsOrderNo;

    /**
     * 收件地址
     */
    private String toAddress;

    /**
     * 收件人
     */
    private String addressee;

    /**
     * 收件人电话
     */
    private String addresseeTelephone;

    /**
     * 发件地址
     */
    private String  sendingAddress;

    /**
     * 发件人
     */
    private String from;

    /**
     * 发件人电话
     */
    private String fromTelephone;
}