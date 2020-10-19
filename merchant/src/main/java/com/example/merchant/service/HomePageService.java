package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.merchant.exception.CommonException;

import javax.servlet.http.HttpServletRequest;

public interface HomePageService {
    ReturnJson getHomePageInof(HttpServletRequest request);
    ReturnJson getHomePageInofpaas(HttpServletRequest request)throws CommonException;
}
