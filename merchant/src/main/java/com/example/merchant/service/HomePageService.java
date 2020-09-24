package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.merchant.exception.CommonException;

public interface HomePageService {
    ReturnJson getHomePageInof(String merchantId);
    ReturnJson getHomePageInofpaas(String managersId)throws CommonException;
}
