package com.example.common.lianlianpay.utils;

import sun.misc.BASE64Decoder;

import java.io.UnsupportedEncodingException;

public class Base64 {
    public static String getBASE64(String s) {
        if (s == null) {
            return null;
        }
        try {
            return (new sun.misc.BASE64Encoder()).encode(s.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getBASE64(byte[] b) {
        return (new sun.misc.BASE64Encoder()).encode(b);
    }

    /**
     * 将 BASE64 编码的字符串 s 进行解码
     *
     * @param s
     * @return
     */
    public static String getFromBASE64(String s) {
        if (s == null) {
            return null;
        }

        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b, "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将 BASE64 编码的字符串 s 进行解码
     *
     * @param s
     * @return
     */
    public static byte[] getBytesBASE64(String s) {
        if (s == null) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return b;
        } catch (Exception e) {
            return null;
        }
    }
}
