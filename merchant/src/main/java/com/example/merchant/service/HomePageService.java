package com.example.merchant.service;

import com.example.common.util.ReturnJson;

public interface HomePageService {
    ReturnJson getHomePageInof(String merchantId);
    ReturnJson getHomePageInofpaas(String managersId);
}
