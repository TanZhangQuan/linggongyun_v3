package com.example.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 识别身份证信息工具类
 */
public class IdCardUtils {
    /**
     *
     * @param filePath 身份证正面的存储地址
     * @return
     */
    public static Map<String,String> getIdCardInfo(String filePath){
        String accessToken = AuthService.getAuth();
        String front = Idcard.idcard(filePath, accessToken, "front");
        Map<String,Map<String,Map<String,Object>>> mapMap = new HashMap<>();
        Map<String,Map<String,Map<String,Object>>> map = JsonUtils.jsonToPojo(front, mapMap.getClass());
        Map<String, Map<String, Object>> words_result = map.get("words_result");
        Map<String,String> idCardInfo = new HashMap<>();
        idCardInfo.put("name",words_result.get("姓名").get("words").toString());
        idCardInfo.put("idCard",words_result.get("公民身份号码").get("words").toString());
        idCardInfo.put("nation",words_result.get("民族").get("words").toString());
        idCardInfo.put("sex",words_result.get("性别").get("words").toString());
        idCardInfo.put("birth",words_result.get("出生").get("words").toString());
        idCardInfo.put("address",words_result.get("住址").get("words").toString());
        return idCardInfo;
    }

    public static Map<String,String> getIdCardInfo(String filePath,String id_card_side){
        String accessToken = AuthService.getAuth();
        String front = Idcard.idcard(filePath, accessToken, id_card_side);
        Map<String,Map<String,Map<String,Object>>> mapMap = new HashMap<>();
        Map<String,Map<String,Map<String,Object>>> map = JsonUtils.jsonToPojo(front, mapMap.getClass());
        Map<String, Map<String, Object>> words_result = map.get("words_result");
        Map<String,String> idCardInfo = new HashMap<>();
        idCardInfo.put("name",words_result.get("姓名").get("words").toString());
        idCardInfo.put("idCard",words_result.get("公民身份号码").get("words").toString());
        idCardInfo.put("nation",words_result.get("民族").get("words").toString());
        idCardInfo.put("sex",words_result.get("性别").get("words").toString());
        idCardInfo.put("birth",words_result.get("出生").get("words").toString());
        idCardInfo.put("address",words_result.get("住址").get("words").toString());
        return idCardInfo;
    }

    public static Map<String,String> getIdCardInfo(String filePath,String id_card_side,String accessToken){
        String front = Idcard.idcard(filePath, accessToken, id_card_side);
        Map<String,Map<String,Map<String,Object>>> mapMap = new HashMap<>();
        Map<String,Map<String,Map<String,Object>>> map = JsonUtils.jsonToPojo(front, mapMap.getClass());
        Map<String, Map<String, Object>> words_result = map.get("words_result");
        Map<String,String> idCardInfo = new HashMap<>();
        idCardInfo.put("name",words_result.get("姓名").get("words").toString());
        idCardInfo.put("idCard",words_result.get("公民身份号码").get("words").toString());
        idCardInfo.put("nation",words_result.get("民族").get("words").toString());
        idCardInfo.put("sex",words_result.get("性别").get("words").toString());
        idCardInfo.put("birth",words_result.get("出生").get("words").toString());
        idCardInfo.put("address",words_result.get("住址").get("words").toString());
        return idCardInfo;
    }
}
