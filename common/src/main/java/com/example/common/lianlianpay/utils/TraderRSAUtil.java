//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.common.lianlianpay.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

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

}
