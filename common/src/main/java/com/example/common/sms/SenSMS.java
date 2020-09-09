package com.example.common.sms;

import com.example.common.Tools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SenSMS {

    @Value("${serverIP}")
    private  String serverIP;

    @Value("${serverPort}")
    private  String serverPort;

    @Value("${AccountSid}")
    private  String AccountSid;

    @Value("${AccountToken}")
    private  String AccountToken;

    @Value("${AppId}")
    private  String AppId;

    @Value("${TemplateId}")
    private  String TemplateId;

    public  Map<String,Object> senSMS(String mobileCode){
        CCPRestSDK restAPI = new CCPRestSDK();
        Map<String,Object> result = new HashMap<>();
        restAPI.init(serverIP, serverPort);// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
        restAPI.setAccount(AccountSid, AccountToken);// 初始化主帐号和主帐号TOKEN
        restAPI.setAppId(AppId);// 初始化应用ID
        String checkCode = String.valueOf(Tools.getRandomNum()); //验证码
        result = restAPI.sendTemplateSMS(mobileCode, TemplateId, new String[]{checkCode, "10"});
        result.put("checkCode",checkCode);
        return result;
    }
}
