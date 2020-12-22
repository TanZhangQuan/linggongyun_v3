package com.example.merchant.vo.platform;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/7
 */
@Data
public class QueryMerchantInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账户信息ID
     */
    private String id;

    /**
     * 登录密码
     */
    private String passWord;

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

    /**
     * 真实姓名
     */
    private String realName;
}
