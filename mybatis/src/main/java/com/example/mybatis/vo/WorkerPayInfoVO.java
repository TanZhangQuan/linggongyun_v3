package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/2
 */
@Data
public class WorkerPayInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付Id
     */
    private String paymentOrderId;

    /**
     * 商户名称
     */
    private String companySName;

    /**
     * 平台服务商名称
     */
    private String platformServiceProvider;

    /**
     * 创客名称
     */
    private String workerName;

    /**
     * 0总包，1众包
     */
    private Integer packageStatus;

    /**
     * 流水金额
     */
    private Integer taskMoney;

    /**
     * 支付状态
     */
    private Integer paymentStatus;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;
}
