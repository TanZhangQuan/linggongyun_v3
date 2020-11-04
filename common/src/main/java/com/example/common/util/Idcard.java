package com.example.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.net.URLEncoder;
import java.util.Map;

/**
 * 身份证识别
 */
@Slf4j
public class Idcard {
    /**
     * @param filePath
     * @param accessToken
     * @param id_card_side front:身份证正面/back身份证反面
     * @return
     */
    public static String idcard(String filePath, String accessToken, String id_card_side) throws Exception {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        // 本地文件路径
//            byte[] imgData = FileUtil.readFileByBytes(filePath);
        // 网络图片
        byte[] imgData = FileUtil.readFileUrlByBytes(filePath);

        String imgStr = com.baidu.aip.util.Base64Util.encode(imgData);
        String imgParam = URLEncoder.encode(imgStr, "UTF-8");

        String param = "id_card_side=" + id_card_side + "&image=" + imgParam;

        String result = HttpUtil.post(url, accessToken, param);
        log.info(result);
        return result;
    }

    public static void main(String[] args) throws Exception {
        String auth = AuthService.getAuth();
        String front = Idcard.idcard("http://diyi-cr.oss-cn-shanghai.aliyuncs.com/upload/20201103/2dc6c1dc5c4d4e2da868fd32069348c3.png", auth, "front");
        System.out.println(front);
//        Map<String,Map<String,Map<String,Object>>> mapMap = new HashMap<>();
//        Map<String,Map<String,Map<String,Object>>> map = JsonUtils.jsonToPojo(front, mapMap.getClass());
//        Map<String, Map<String, Object>> words_result = map.get("words_result");
//        System.out.println(words_result.get("姓名").get("words"));
//        System.out.println(words_result.get("公民身份号码").get("words"));
//        Map<String, String> idCardInfo = IdCardUtils.getIdCardInfo("D:/upload/image/5f8cfeead3eb4d4982b62a30d274eadd.jpg");
//        System.out.println(idCardInfo.toString());
    }
}