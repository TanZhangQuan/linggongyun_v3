package com.example.merchant.dto.platform;

import lombok.Data;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/2
 */
@Data
public class AddMerchantDto {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 支付密码
     */
    private String payPwd;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 登录密码
     */
    private String passWord;

    /**
     * 登录时用的手机号码
     */
    private String loginMobile;
}
