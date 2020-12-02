package com.example.merchant.dto.platform;

import lombok.Data;

/**
 * @Description 开票信息
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/2
 */
@Data
public class AddCompanyInvoiceInfoDto {

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
