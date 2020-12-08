package com.example.merchant.vo.platform;

import lombok.Data;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/7
 */
@Data
public class QueryInvoiceInfoVo {

    /**
     * 商户开票信息ID
     */
    private String id;

    /**
     * 公司全称
     */
    private String companyName;

    /**
     * 公司地址
     */
    private String companyAddress;

    /**
     * 纳税识别号
     */
    private String taxCode;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankCode;

    /**
     * 电话
     */
    private String mobile;
}
