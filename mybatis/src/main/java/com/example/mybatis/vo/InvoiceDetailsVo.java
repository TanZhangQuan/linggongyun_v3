package com.example.mybatis.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 众包开票明细
 */
@Data
public class InvoiceDetailsVo {

    /**
     * 支付清单id
     */
    private String id;
    /**
     * 创客编号
     */
    private String workerId;
    /**
     * 创客名称
     */
    private String workerName;
    /**
     * 创客电话号码
     */
    private String mobileCode;
    /**
     * 创客身份证
     */
    private String idCardCode;
    /**
     * 创客银行卡号
     */
    private String bankCode;
    /**
     * 创客到手费用
     */
    private String realMoney;
    /**
     * 任务金额
     */
    private BigDecimal taskMoney;
    /**
     * 服务商id
     */
    private String taxId;
    /**
     * 创客承担税率
     */
    private BigDecimal receviceTax;

    /**
     * 纳税金额
     */
    private BigDecimal taxAmount;

    /**
     * 纳税率
     */
    private BigDecimal taxRate;

    /**
     * 个人服务费
     */
    private BigDecimal personalServiceFee;

}
