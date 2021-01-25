package com.example.common.util;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 快递鸟物流轨迹即时查询接口
 *
 * @技术QQ群: 456320272
 * @see: http://www.kdniao.com/YundanChaxunAPI.aspx
 * @copyright: 深圳市快金数据技术服务有限公司
 * <p>
 * DEMO中的电商ID与私钥仅限测试使用，正式环境请单独注册账号
 * 单日超过500单查询量，建议接入我方物流轨迹订阅推送接口
 * <p>
 * ID和Key请到官网申请：http://www.kdniao.com/ServiceApply.aspx
 */

@Slf4j
public class KdniaoTrackQueryAPI {

    private static char[] base64EncodeChars = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/'};
    //电商ID
    private String EBusinessID = "1613650";
    //电商加密私钥，快递鸟提供，注意保管，不要泄漏
    private String AppKey = "a24f5b98-9d7a-4ef9-8672-8cdc3587fed1";
    //请求url
    private String ReqURL = "http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";

    //DEMO
    public static void main(String[] args) {
        KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
        try {
            //String result = api.getOrderTracesByJson("YTO", "YT4733477430882");
            List<ExpressLogisticsInfo> expressLogisticsInfos = getExpressInfo("申通速递", "777027340407442");
            System.out.println(expressLogisticsInfos.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取快递单号
     *
     * @param expressCompanyName 快递公司名称
     * @param expressCode        快递单号
     * @return
     */
    public static List<ExpressLogisticsInfo> getExpressInfo(String expressCompanyName, String expressCode) {
        DateTimeFormatter dfd = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<ExpressLogisticsInfo> expressLogisticsInfos = new ArrayList<>();
        try {
            KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
            String expressCompanyCode = getExpressCompanyCode(expressCompanyName);
            String result = api.getOrderTracesByJson(expressCompanyCode, expressCode);
            JSONObject jsonObject = JSONObject.fromObject(result);

            String Success = jsonObject.getString("Success");
            if ("false".equals(Success)) {
                ExpressLogisticsInfo expressInfo = new ExpressLogisticsInfo();
                expressInfo.setAcceptStation("暂时没有快递信息");
                expressInfo.setAcceptTime(LocalDateTime.now());
                expressLogisticsInfos.add(expressInfo);
                String Reason = jsonObject.getString("Reason");
                log.error(Reason);
                return expressLogisticsInfos;
            }
            String State = jsonObject.getString("State");
            if ("0".equals(State)) {
                ExpressLogisticsInfo expressInfo = new ExpressLogisticsInfo();
                expressInfo.setAcceptStation("暂时没有快递信息");
                expressInfo.setAcceptTime(LocalDateTime.now());
                expressLogisticsInfos.add(expressInfo);
            }
            JSONArray Traces = jsonObject.getJSONArray("Traces");
            for (int i = 0; i < Traces.size(); i++) {
                JSONObject object = (JSONObject) Traces.get(i);
                ExpressLogisticsInfo expressInfo = new ExpressLogisticsInfo();
                expressInfo.setAcceptStation(object.getString("AcceptStation"));
                expressInfo.setAcceptTime(LocalDateTime.parse(object.getString("AcceptTime"), dfd));
                expressLogisticsInfos.add(expressInfo);
            }
            return expressLogisticsInfos;
        } catch (Exception e) {
            log.error(e.toString() + ":" + e.getMessage());
        }
        return expressLogisticsInfos;
    }

    public static String base64Encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }

    /**
     * 获取快递公司编号
     *
     * @param expressCompanyName 快递公司名称
     * @return
     */
    private static String getExpressCompanyCode(String expressCompanyName) {
        String expressCompanyCode = "";
        switch (expressCompanyName) {
            case "顺丰速运":
                expressCompanyCode = "SF";
                break;
            case "百世快递":
                expressCompanyCode = "HTKY";
                break;
            case "中通快递":
                expressCompanyCode = "ZTO";
                break;
            case "申通快递":
                expressCompanyCode = "STO";
                break;
            case "圆通速递":
                expressCompanyCode = "YTO";
                break;
            case "韵达速递":
                expressCompanyCode = "YD";
                break;
            case "邮政快递":
                expressCompanyCode = "YZPY";
                break;
            case "EMS":
                expressCompanyCode = "EMS";
                break;
            case "天天快递":
                expressCompanyCode = "HHTT";
                break;
            case "优速快递":
                expressCompanyCode = "UC";
                break;
            case "德邦快递":
                expressCompanyCode = "DBL";
                break;
            case "宅急送":
                expressCompanyCode = "ZJS";
                break;
            case "京东快递":
                expressCompanyCode = "JD";
                break;
        }
        return expressCompanyCode;
    }

    /**
     * Json方式 查询订单物流轨迹
     *
     * @throws Exception
     */
    public String getOrderTracesByJson(String expCode, String expNo) throws Exception {
        String requestData = "{'OrderCode':'','ShipperCode':'" + expCode + "','LogisticCode':'" + expNo + "'}";

        Map<String, String> params = new HashMap<String, String>();
        params.put("RequestData", urlEncoder(requestData, "UTF-8"));
        params.put("EBusinessID", EBusinessID);
        params.put("RequestType", "1002");
        String dataSign = encrypt(requestData, AppKey, "UTF-8");
        params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
        params.put("DataType", "2");

        String result = sendPost(ReqURL, params);

        //根据公司业务处理返回的信息......

        return result;
    }

    /**
     * MD5加密
     *
     * @param str     内容
     * @param charset 编码方式
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * base64编码
     *
     * @param str     内容
     * @param charset 编码方式
     * @throws UnsupportedEncodingException
     */
    private String base64(String str, String charset) throws UnsupportedEncodingException {
        String encoded = base64Encode(str.getBytes(charset));
        return encoded;
    }

    @SuppressWarnings("unused")
    private String urlEncoder(String str, String charset) throws UnsupportedEncodingException {
        String result = URLEncoder.encode(str, charset);
        return result;
    }

    /**
     * 电商Sign签名生成
     *
     * @param content  内容
     * @param keyValue Appkey
     * @param charset  编码方式
     * @return DataSign签名
     * @throws UnsupportedEncodingException ,Exception
     */
    @SuppressWarnings("unused")
    private String encrypt(String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception {
        if (keyValue != null) {
            return base64(MD5(content + keyValue, charset), charset);
        }
        return base64(MD5(content, charset), charset);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url    发送请求的 URL
     * @param params 请求的参数集合
     * @return 远程资源的响应结果
     */
    @SuppressWarnings("unused")
    private String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            if (params != null) {
                StringBuilder param = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (param.length() > 0) {
                        param.append("&");
                    }
                    param.append(entry.getKey());
                    param.append("=");
                    param.append(entry.getValue());
                    //System.out.println(entry.getKey()+":"+entry.getValue());
                }
                //System.out.println("param:"+param.toString());
                out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

}
