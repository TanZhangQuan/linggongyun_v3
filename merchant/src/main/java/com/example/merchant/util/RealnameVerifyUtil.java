package com.example.merchant.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * 实名认证工具
 *
 * @author tzq
 * @since 2020/6/27 18:00
 */
@Slf4j
public class RealnameVerifyUtil {

    /***
     *
     * @param str 待计算的消息
     * @return MD5计算后摘要值的Base64编码(ContentMD5)
     * @throws Exception 加密过程中的异常信息
     */
    public static String doContentMD5(String str) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        // 计算md5函数
        md5.update(str.getBytes("UTF-8"));
        // 查询文件MD5的二进制数组（128位）
        byte[] md5Bytes = md5.digest();
        // 把MD5摘要后的二进制数组md5Bytes使用Base64进行编码（而不是对32位的16进制字符串进行编码）
        String contentMD5 = new String(Base64.encodeBase64(md5Bytes), "UTF-8");
        return contentMD5;
    }

    /***
     * 计算请求签名值
     *
     * @param message 待计算的消息
     * @param secret 密钥
     * @return HmacSHA256计算后摘要值的Base64编码
     * @throws Exception 加密过程中的异常信息
     */
    public static String doSignatureBase64(String message, String secret) throws Exception {
        String algorithm = "HmacSHA256";
        String digestBase64;

        Mac hmacSha256 = Mac.getInstance(algorithm);
        byte[] keyBytes = secret.getBytes("UTF-8");
        byte[] messageBytes = message.getBytes("UTF-8");
        hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, algorithm));
        // 使用HmacSHA256对二进制数据消息Bytes计算摘要
        byte[] digestBytes = hmacSha256.doFinal(messageBytes);
        // 把摘要后的结果digestBytes转换成十六进制的字符串
        // String digestBase64 = Hex.encodeHexString(digestBytes);
        // 把摘要后的结果digestBytes使用Base64进行编码
        digestBase64 = new String(Base64.encodeBase64(digestBytes), "UTF-8");

        return digestBase64;
    }

    /***
     * 查询时间戳(毫秒级)
     *
     * @return 毫秒级时间戳, 如 1578446909000
     */
    public static long timeStamp() {
        long timeStamp = System.currentTimeMillis();
        return timeStamp;
    }

    public static boolean checkPass(HttpServletRequest request, String rbody, String appSecret) throws Exception {

        String signture = request.getHeader("X-Tsign-Open-SIGNATURE");
        //1. 查询时间戳的字节流
        String timestamp = request.getHeader("X-Tsign-Open-TIMESTAMP");
//		String content_type  =request
        //2. 查询query请求字符串
        String requestQuery = getRequestQueryStr(request);
        //4、按照规则进行加密
        String signdata = timestamp + requestQuery + rbody;
        String mySignature = getSignature(signdata, appSecret, "HmacSHA256", "UTF-8");
        log.info("加密出来的签名值：----------->>>>>>" + mySignature);
        log.info("header里面的签名值：---------->>>>>>" + signture);
        if (mySignature.equals(signture)) {
            log.info("校验通过");
            return true;
        } else {
            log.info("校验失败");
            return false;
        }

    }

    /**
     * 查询请求body
     *
     * @param request
     * @param encoding
     * @return
     */
    public static String getRequestBody(HttpServletRequest request, String encoding) throws IOException {
        // 请求内容RequestBody
        String reqBody = null;
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];

        for (int i = 0; i < contentLength; ) {
            int readlen = request.getInputStream().read(buffer, i, contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }

        return new String(buffer, encoding);
    }

    /**
     * 查询query请求字符串
     *
     * @param request
     * @return
     */
    public static String getRequestQueryStr(HttpServletRequest request) {
        //对 Query 参数按照字典对 Key 进行排序后,按照value1+value2方法拼接
        //转换一下数据类型并排序
        List<String> req_List = new ArrayList();
        Enumeration<String> reqEnu = request.getParameterNames();
        while (reqEnu.hasMoreElements()) {
            req_List.add(reqEnu.nextElement());
        }
        Collections.sort(req_List);
        String requestQuery = "";
        for (String key : req_List) {
            String value = request.getParameter(key);
            requestQuery += value == null ? "" : value;
        }
        log.info("查询的query请求字符串是：------》》》" + requestQuery);
        return requestQuery;
    }

    /***
     * 查询请求签名值
     *
     * @param data
     *            加密前数据
     * @param key
     *            密钥
     * @param algorithm
     *            HmacMD5 HmacSHA1 HmacSHA256 HmacSHA384 HmacSHA512
     * @param encoding
     *            编码格式
     * @return HMAC加密后16进制字符串
     * @throws Exception
     */
    public static String getSignature(String data, String key, String algorithm, String encoding) throws Exception {

        Mac mac = Mac.getInstance(algorithm);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(encoding), algorithm);
        mac.init(secretKey);
        mac.update(data.getBytes(encoding));
        return byte2hex(mac.doFinal());
    }

    /***
     * 将byte[]转成16进制字符串
     *
     * @param data
     *
     * @return 16进制字符串
     */
    public static String byte2hex(byte[] data) {
        StringBuilder hash = new StringBuilder();
        String stmp;
        for (int n = 0; data != null && n < data.length; n++) {
            stmp = Integer.toHexString(data[n] & 0XFF);
            if (stmp.length() == 1)
                hash.append('0');
            hash.append(stmp);
        }
        return hash.toString();
    }

}
