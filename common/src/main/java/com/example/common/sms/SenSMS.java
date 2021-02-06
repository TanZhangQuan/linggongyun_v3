package com.example.common.sms;

import com.example.common.config.YuntongxunConfig;
import com.example.common.util.Tools;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SenSMS {

    public Map<String, Object> senSMS(String mobileCode) {
        CCPRestSDK restAPI = new CCPRestSDK();
        restAPI.init(YuntongxunConfig.getServerIp(), YuntongxunConfig.getServerPort());// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
        restAPI.setAccount(YuntongxunConfig.getAccountSid(), YuntongxunConfig.getAuthToken());// 初始化主帐号和主帐号TOKEN
        restAPI.setAppId(YuntongxunConfig.getAppId());// 初始化应用ID
        String checkCode = String.valueOf(Tools.getRandomNum()); //验证码
        Map<String, Object> result = restAPI.sendTemplateSMS(mobileCode, YuntongxunConfig.getTemplateId(), new String[]{checkCode, "10"});
        result.put("checkCode", checkCode);
        return result;
    }
}
