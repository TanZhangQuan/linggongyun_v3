package com.example.common.util;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
* 身份证识别
*/
@Slf4j
public class Idcard {
    /**
     *
     * @param filePath
     * @param accessToken
     * @param id_card_side  front:身份证正面/back身份证反面
     * @return
     */
    public static String idcard(String filePath,String accessToken,String id_card_side) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        try {
            // 本地文件路径
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "id_card_side=" + id_card_side + "&image=" + imgParam;

            String result = HttpUtil.post(url, accessToken, param);
            log.info(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//        String auth = AuthService.getAuth();
//        String front = Idcard.idcard("G:/upload/image/20200925205103.jpg", auth, "front");
//        Map<String,Map<String,Map<String,Object>>> mapMap = new HashMap<>();
//        Map<String,Map<String,Map<String,Object>>> map = JsonUtils.jsonToPojo(front, mapMap.getClass());
//        Map<String, Map<String, Object>> words_result = map.get("words_result");
//        System.out.println(words_result.get("姓名").get("words"));
//        System.out.println(words_result.get("公民身份号码").get("words"));
        Map<String, String> idCardInfo = IdCardUtils.getIdCardInfo("G:/upload/image/a9e57dd19d474515a3bd2a6a3c62b1ba.jpg");
        System.out.println(idCardInfo.toString());
    }
}