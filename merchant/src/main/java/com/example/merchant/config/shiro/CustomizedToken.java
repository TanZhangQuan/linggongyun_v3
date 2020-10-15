package com.example.merchant.config.shiro;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

@Data
public class CustomizedToken extends UsernamePasswordToken
{
    /**
     *
     */
    private static final long serialVersionUID = -4890297065880698142L;

    //登录类型，判断是商户登录，还是管理员登录
    private String loginType;

    public CustomizedToken(final String username, final String password,String loginType) {
        super(username,password);
        this.loginType = loginType;
    }
}