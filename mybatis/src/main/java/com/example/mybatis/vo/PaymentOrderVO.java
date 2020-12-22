package com.example.mybatis.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付清单
 */
@Data
public class PaymentOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String companySName;

    private String platformServiceProvider;

    private String acceptanceCertificate;

    private String paymentOrderStatus;

    private String paymentInventory;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date paymentDate;

    private String subpackagePayment;

    private TaskVO taskVo;
}
