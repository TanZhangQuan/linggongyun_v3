package com.example.mybatis.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 支付清单
 */
@Data
public class PaymentOrderVo {

    private String id;

    private String companySName;

    private String platformServiceProvider;

    private String acceptanceCertificate;

    private String paymentOrderStatus;

    private String paymentInventory;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date paymentDate;

    private String subpackagePayment;

    private TaskVo taskVo;
}
