package com.example.mybatis.vo;

import lombok.Data;

/**
 * 购买方
 */
@Data
public class BuyerVo {
    //名称
    private String companyName;
    //纳税人识别号
    private String creditCode;
    //地址
    private String companyAddress;
    //电话
    private String telephones;
    //开户行
    private String bankName;
    //卡号
    private String bankCode;
}
