package com.example.common.util;

import com.example.common.enums.IdCardSide;

import java.util.HashMap;
import java.util.Map;

/**
 * 识别身份证信息工具类
 */
public class IdCardUtils {
    /**
     * @param filePath 身份证正面的存储地址
     * @return
     */
    public static Map<String, String> getIdCardInfo(String filePath, IdCardSide idCardSide) throws Exception {
        String accessToken = AuthService.getAuth();
        String result = Idcard.idcard(filePath, accessToken, idCardSide.getValue());
        Map<String, Map<String, Map<String, Object>>> mapMap = new HashMap<>();
        Map<String, Map<String, Map<String, Object>>> map = JsonUtils.jsonToPojo(result, mapMap.getClass());
        Map<String, Map<String, Object>> words_result = map.get("words_result");
        if (words_result == null) {
            return null;
        }
        Map<String, String> idCardInfo = new HashMap<>();
        if ("back".equals(idCardSide.getValue())) {
            idCardInfo.put("expiryDate", words_result.get("失效日期") == null ? "" : words_result.get("失效日期").get("words").toString());
            idCardInfo.put("issuingAuthority", words_result.get("签发机关") == null ? "" : words_result.get("签发机关").get("words").toString());
            return idCardInfo;
        }
        idCardInfo.put("name", words_result.get("姓名") == null ? "" : words_result.get("姓名").get("words").toString());
        idCardInfo.put("idCard", words_result.get("公民身份号码") == null ? "" : words_result.get("公民身份号码").get("words").toString());
        idCardInfo.put("nation", words_result.get("民族") == null ? "" : words_result.get("民族").get("words").toString());
        idCardInfo.put("sex", words_result.get("性别") == null ? "" : words_result.get("性别").get("words").toString());
        idCardInfo.put("birth", words_result.get("出生") == null ? "" : words_result.get("出生").get("words").toString());
        idCardInfo.put("address", words_result.get("住址") == null ? "" : words_result.get("住址").get("words").toString());
        return idCardInfo;
    }
}
