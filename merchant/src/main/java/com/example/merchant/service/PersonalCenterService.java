package com.example.merchant.service;

import com.example.common.util.ReturnJson;

public interface PersonalCenterService {
    /**
     * 个人信息
     *
     * @param workerId
     * @return
     */
    ReturnJson personageInfo(String workerId);
}
