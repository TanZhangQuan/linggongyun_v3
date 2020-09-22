package com.example.mybatis.vo;

import lombok.Data;

/**
 * 销售方
 */
@Data
public class SellerVo {
    //税源地名称
    private String taxName;
    //纳税人识别号
    private String invoiceTaxno;
    //地址
    private String taxAddress;
    //电话
    private String phone;
    //开户行
    private String bankName;
    //银行号码
    private String bankCode;
}
