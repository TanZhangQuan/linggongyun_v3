package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author .
 * @date 2021/2/2.
 * @time 11:09.
 */
@Data
public class SalesmanSAndAgentDetailVO implements Serializable {
    /**
     * 众包总包的id
     */
    private String id;
    /**
     * 订单号
     */
    private String tradeNo;
    /**
     * 代理商Id
     */
    private String agentId;
    /**
     * 业务员Id
     */
    private String salesManId;
    /**
     * 业务员名称
     */
    private String salesManName;
    /**
     * 商户名称
     */
    private String companyName;
    /**
     * 服务商名称
     */
    private String taxName;
    /**
     * 代理商名称
     */
    private String agentName;
    /**
     * 0 总包，1众包
     */
    private Integer paymentOrderType;
    /**
     * 总人数
     */
    private Integer peopleNum;
    /**
     * 实发金额
     */
    private BigDecimal workerMoney;
    /**
     * s商户服务费
     */
    private BigDecimal merchantServiceCharge;
    /**
     * 创客服务费
     */
    private BigDecimal workerServiceCharge;
    /**
     * 代理商Id
     */
    private BigDecimal realMoney;
    /**
     * 总提成
     */
    private BigDecimal totalDifference;
}
