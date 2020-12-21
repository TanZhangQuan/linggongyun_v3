package com.example.mybatis.dto;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class ApplicationPaymentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 申请开票ID
     */
    private String invoiceApplicationId;

    /**
     * 总包支付ID,多个支付id以逗号隔开
     */
    private String paymentOrderId;
}
