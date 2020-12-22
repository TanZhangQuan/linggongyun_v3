package com.example.merchant.dto.platform;

import lombok.Data;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/7
 */
@Data
public class UpdateMerchantInfDto {

    /**
     * 账户信息ID
     */
    private String id;

    /**
     * 登录密码
     */
    private String passWord;

    /**
     * 支付密码
     */
    private String payPwd;
    
    /**
     * 登录时用的手机号码
     */
    private String loginMobile;

    /**
     * 商户状态
     */
    private Integer status;

    /**
     * 用户名
     */
    private String userName;
}
