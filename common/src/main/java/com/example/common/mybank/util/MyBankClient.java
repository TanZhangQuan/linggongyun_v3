package com.example.common.mybank.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MyBankClient {

    /**
     * 网银url
     */
    private String gatewayUrl = "http://test.tc.mybank.cn/gop/gateway.do";

    /**
     * 接口版本，设置为2.1
     */
    private String version = "2.1";

    /**
     * 云资金管理平台分配给合作方业务平台签约唯一ID
     */
    private String partnerId = "200002007807";

    /**
     * 合作方业务平台使用的编码格式，如utf-8、gbk、gb2312等
     */
    private String charset = GatewayConstant.charset_utf_8;

    /**
     * 签名方式只支持TWSIGN
     */
    private String signType = "TWSIGN";



    public String myBank(Map<String, String> params) throws Exception {
        params.put("charset", charset);
        params.put("version", version);
        params.put("partner_id", partnerId);
        Map<String, String> send = MagCore.paraFilter2(params);

        String sign = MagCore.buildRequestByTWSIGN(send, "utf-8", GatewayConstant.KEY_STORE_NAME);
        send.put("sign", sign);
        send.put("sign_type", signType);
        return doHttpClientPost(gatewayUrl,send);
    }


    public static String doHttpClientPost(String url, Map<String, String> params) {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String rst = null;
        try {
            //创建一个post对象
            List<NameValuePair> ps = buildPostParams(params);
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(ps, "UTF-8"));
            //执行post请求
            response = httpClient.execute(post);
            System.out.println(response.toString());
            if (response.getStatusLine().getStatusCode() == 200) {// 网关调用成功
                rst = inputStreamToStr(response.getEntity().getContent(), "UTF-8");
                System.out.println("=======================================");
                System.out.println(String.format("httpClient Post调用结果：%s", rst));
                System.out.println("=======================================");
            }
        } catch (Exception e) {
            System.out.println("=======================================");
            System.out.println(String.format("httpClient Post 请求失败：{}", e));
            System.out.println("=======================================");
        } finally {
            try {
                if (null != response)
                    response.close();
                if (null != httpClient)
                    httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rst;
    }

    private static List<NameValuePair> buildPostParams(Map<String, String> params) {
        if (params == null || params.size() == 0)
            return null;
        List<NameValuePair> results = new ArrayList<NameValuePair>();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            results.add(new BasicNameValuePair(key, value));
        }

        return results;
    }

    private static String inputStreamToStr(InputStream is, String charset) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        return new String(buffer.toString().getBytes("ISO-8859-1"), charset);
    }
}
