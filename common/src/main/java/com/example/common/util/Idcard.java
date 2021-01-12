package com.example.common.util;

import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;

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

}