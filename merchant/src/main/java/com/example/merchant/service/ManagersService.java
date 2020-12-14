package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Managers;

import javax.servlet.http.HttpServletResponse;

public interface ManagersService extends IService<Managers> {
    /**
     * 平台端登录
     *
     * @param userName
     * @param passWord
     * @param response
     * @return
     */
    ReturnJson managersLogin(String userName, String passWord, HttpServletResponse response);

    /**
     * 发送验证码
     *
     * @param mobileCode
     * @return
     */
    ReturnJson senSMS(String mobileCode);

    /**
     * 手机号码登录
     *
     * @param loginMobile
     * @param checkCode
     * @param resource
     * @return
     */
    ReturnJson loginMobile(String loginMobile, String checkCode, HttpServletResponse resource);

    /**
     * 获取详细信息
     *
     * @param CustomizedId
     * @return
     */
    ReturnJson getCustomizedInfo(String CustomizedId);

    /**
     * 登出
     *
     * @param manangerId
     * @return
     */
    ReturnJson logout(String manangerId);

    /**
     * 更改密码
     *
     * @param loginMobile
     * @param checkCode
     * @param newPassWord
     * @return
     */
    ReturnJson updataPassWord(String loginMobile, String checkCode, String newPassWord);
}
