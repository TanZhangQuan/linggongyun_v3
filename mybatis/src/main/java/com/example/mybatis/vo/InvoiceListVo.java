package com.example.mybatis.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 开票清单明细信息
 */
@Data
public class InvoiceListVo {

    /**
     * 创客名称
     */
    private String workerName;
    /**
     * 创客电话
     */
    private String mobileCode;
    /**
     * 身份证号
     */
    private String idCardCode;
    /**
     * 银行卡号
     */
    private String bankCode;
    /**
     * 创客到手实际金额
     */
    private BigDecimal realMoney;
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
    private String receviceTax;

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
