//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.common.lianlianpay.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class TraderRSAUtil {
    private static Logger log = Logger.getLogger(TraderRSAUtil.class);
    private static TraderRSAUtil instance;

    private TraderRSAUtil() {
    }

    public static TraderRSAUtil getInstance() {
        return instance == null ? new TraderRSAUtil() : instance;
    }

    private void generateKeyPair(String key_path, String name_prefix) {
        KeyPairGenerator keygen = null;

        try {
            keygen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException var13) {
            log.error(var13.getMessage());
        }

        SecureRandom secrand = new SecureRandom();
        secrand.setSeed("3500".getBytes());
        keygen.initialize(1024, secrand);
        KeyPair keys = keygen.genKeyPair();
        PublicKey pubkey = keys.getPublic();
        PrivateKey prikey = keys.getPrivate();
        String pubKeyStr = new String(Base64.encodeBase64(pubkey.getEncoded()));
        String priKeyStr = new String(Base64.encodeBase64(Base64.encodeBase64(prikey.getEncoded())));
        File file = new File(key_path);
        if (!file.exists()) {
            file.mkdirs();
        }

        try {
            FileOutputStream fos = new FileOutputStream(new File(key_path + name_prefix + "_RSAKey_private.txt"));
            fos.write(priKeyStr.getBytes());
            fos.close();
            fos = new FileOutputStream(new File(key_path + name_prefix + "_RSAKey_public.txt"));
            fos.write(pubKeyStr.getBytes());
            fos.close();
        } catch (IOException var12) {
            log.error(var12.getMessage());
        }

    }

    private static String getKeyContent(String key_file) {
        File file = new File(key_file);
        BufferedReader br = null;
        InputStream ins = null;
        StringBuffer sReturnBuf = new StringBuffer();

        try {
            ins = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
            String readStr = null;

            for(readStr = br.readLine(); readStr != null; readStr = br.readLine()) {
                sReturnBuf.append(readStr);
            }

            return sReturnBuf.toString();
        } catch (IOException var18) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                    br = null;
                } catch (IOException var17) {
                    var17.printStackTrace();
                }
            }

            if (ins != null) {
                try {
                    ins.close();
                    ins = null;
                } catch (IOException var16) {
                    var16.printStackTrace();
                }
            }

        }

        return null;
    }

    public static String sign(String prikeyvalue, String sign_str) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(com.example.common.lianlianpay.utils.Base64.getBytesBASE64(prikeyvalue));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
            Signature signet = Signature.getInstance("MD5withRSA");
            signet.initSign(myprikey);
            signet.update(sign_str.getBytes("UTF-8"));
            byte[] signed = signet.sign();
            return new String(Base64.encodeBase64(signed));
        } catch (Exception var7) {
            log.error("签名失败," + var7.getMessage());
            return null;
        }
    }

    public static boolean checksign(String pubkeyvalue, String oid_str, String signed_str) {
        try {
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(com.example.common.lianlianpay.utils.Base64.getBytesBASE64(pubkeyvalue));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
            byte[] signed = com.example.common.lianlianpay.utils.Base64.getBytesBASE64(signed_str);
            Signature signetcheck = Signature.getInstance("MD5withRSA");
            signetcheck.initVerify(pubKey);
            signetcheck.update(oid_str.getBytes("UTF-8"));
            return signetcheck.verify(signed);
        } catch (Exception var8) {
            log.error("签名验证异常," + var8.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        getInstance();
        String sign = sign("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJPb6UtHkRtCmunLtxgWUUkqKVMqdMrvLxU4UzTRaNddI2tHUszyTSntfz+l1S3BjRBvjx1/yvrFRvneW7lmM9w+e5LPUnIhqnNrl2aeioOJWHz+Ba6qrRXz8kCf6kfsAMG4H2A2xMcb26ZiMPZxFKHinuKcW7bT+bXTFxrQsR/JAgMBAAECgYEAh2vK6F/LzyPZrngeYblPCavL3ZftEFCw1saXrrB9TYLIheD1PTBO7C/RdAH2lcnH4V3LvkDlL3iv4Pp/F/c7Vvvgs/LbpXwnPvYVtdkZ1x3AZRfS/5uSrSoAkiN0zEJnmb3Ywp7YlCYfVlke4u6dhQN+WxvqPl69VMBzNpagXWECQQDlBVUvIqQp6e0Gsp4oOj3HyQtCT+BsaRZkLtMNTq5pcz/83s1H0cIoU8dTT7LCZvRw+yjYgQ5YBY9D0CZBmwdfAkEApUbzmt2klNpf2apadyI+fYcbYBky3kb2q6YZ/xQuCU8eSJC4F2bPDDfxpsIqADj5A8KB74EnB6h1UT9rQONx1wJBAMXuFfDmv3p58aAYPxgFPd+soU5uOkd3iyKKVVzq41G/iU3CQSgQ4Px5a4tVFeltkVUTu/lhkEQCig7Rlj6c/YECQHwqUIrQ5nsZj5bDv1Du/glp/ev1Il0Q7PHJSJB0RZ2ivbqAVnzmNLgWM0o3ZjxikNj9QIaA/aRoLzLJtTa7aGMCQFEkTk/9gIYYKolMwMllO/SN+dO54W1Pc/Dx65ZsEwzgq+UEBb0BjbxbVebVRcaXam6OKIuCW2KwdQuMlY6AqeQ=", "123");
        System.out.println(sign);
    }
}
