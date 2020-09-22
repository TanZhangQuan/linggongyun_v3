package com.example.mybatis.vo;

import lombok.Data;

/**
 * 开票明细
 */
@Data
public class InvoiceDetailsVo {
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
}
