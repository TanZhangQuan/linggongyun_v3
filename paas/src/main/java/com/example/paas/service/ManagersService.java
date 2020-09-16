package com.example.paas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Managers;

import javax.servlet.http.HttpServletResponse;

public interface ManagersService extends IService<Managers>{
    ReturnJson managersLogin(String userName, String passWord, HttpServletResponse response);
    ReturnJson senSMS(String mobileCode);
    ReturnJson loginMobile(String loginMobile,String checkCode, HttpServletResponse resource);
}
