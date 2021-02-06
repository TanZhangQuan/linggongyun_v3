package com.example.merchant.service.impl;

import com.example.common.config.MyBankConfig;
import com.example.common.mybank.util.*;
import com.example.common.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Slf4j
@Service
public class SecurityService {

    /**
     * 签名
     *
     * @param data
     * @param charset
     * @param signType
     * @return
     */
    public SignData sign(Map<String, String> data, String charset, String signType) {
        SignData signData = new SignData();
        signData.setSignType(signType);

        Map<String, String> src = MagCore.paraFilter(data);
        if (log.isInfoEnabled()) {
            log.info("签名原字符串：{}", MagCore.createLinkString(src, false));
        }
        try {
            if (SignType.RSA.getCode().equals(signType)) {
                signData.setSign(MagCore.buildRequestByRSA(src,
                        MyBankConfig.getPlatformPrivateKey(), charset));
                signData.setSuccess(true);
            } else if (SignType.MD5.getCode().equals(signType)) {
                signData.setSign(MagCore.buildRequestByMD5(src,
                        MyBankConfig.getMd5SecretKey(), charset));
                signData.setSuccess(true);
            } else if (SignType.TWSIGN.getCode().equals(signType)) {
                signData.setSign(MagCore.buildRequestByTWSIGN(src, charset, GatewayConstant.KEY_STORE_NAME));
                signData.setSuccess(true);
            } else {
                signData.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            signData.setSuccess(false);
        }

        try {
            Map<String, String> result = MagCore.buildRequestPara(src, signType, MyBankConfig.getPlatformPrivateKey(), data.get("service"), charset);
            String linkString = MagCore.createLinkString(result, true);
            signData.setSignLinkStr(linkString);
        } catch (Exception e) {
            e.printStackTrace();
            signData.setSignLinkStr("错误拼接请求信息");
        }

        return signData;
    }

    /**
     * 验证签名
     *
     * @param map
     * @param charset
     * @param signType
     * @return
     */
    public boolean verify(Map<String, String> map, String charset, String sign, String signType) {
        Map<String, String> tmp = MagCore.paraFilter(map);
        String str = MagCore.createLinkString(tmp, false);
        if ("MD5".equalsIgnoreCase(signType)) {
            return verifyMd5(str, sign, charset);
        } else if ("RSA".equalsIgnoreCase(signType)) {
            return verifyRSA(str, sign, charset);
        }
        return false;
    }

    private boolean verifyMd5(String src, String sign, String charset) {
        boolean result = false;
        if (log.isInfoEnabled()) {
            log.info("verify sign with MD5:src ={},sign={}", new Object[]{src, sign});
        }
        try {
            result = MD5.verify(src, sign, MyBankConfig.getMd5SecretKey(), charset);
        } catch (Exception e) {
            log.error("MD5 verify failure:src ={},sign={}", new Object[]{src, sign});
            log.error("MD5 verify failure", e);
        }
        return result;
    }

    private boolean verifyRSA(String src, String sign, String charset) {
        boolean result = false;
        if (log.isInfoEnabled()) {
            log.info("verify sign with RSA:src ={},sign={}", new Object[]{src, sign});
        }
        try {
            result = RSA.verify(src, sign, MyBankConfig.getPublicKey(), charset);
        } catch (Exception e) {
            log.error("RSA verify failure:src ={},sign={}", new Object[]{src, sign});
            log.error("RSA verify failure", e);
        }
        return result;
    }

    /**
     * 使用钱包公钥加密
     *
     * @param src
     * @param charset
     * @return
     */
    public String encrypt(String src, String charset) {
        if (log.isDebugEnabled()) {
            log.debug("encrypt src:{} ,charset:{} with:RSA", src, charset);
        }

        try {
            byte[] bytes = RSA.encryptByPublicKey(src.getBytes(charset), MyBankConfig.getPublicKey());
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            log.error("RSA encrypt failure:src={},charset={}", src, charset);
            log.error("RSA encrypt failure", e);
        }
        return null;
    }


    public void encrypt(Map<String, String> data, String charset) {
        String service = data.get(BaseField.SERVICE.getCode());
        if ("create_bank_card".equals(service)) {
            String bank_account_no = data.get("bank_account_no_src");
            String account_name = data.get("account_name_src");
            if (StringUtils.hasText(bank_account_no)) {
                String enc = encrypt(bank_account_no, charset);
                data.put("bank_account_no", enc);
            }

            if (StringUtils.hasText(account_name)) {
                String enc = encrypt(account_name, charset);
                data.put("account_name", enc);
            }
        }
    }

    public void filter(Map<String, String> data) {
        if (data.containsKey("bank_account_no_src")) {
            data.remove("bank_account_no_src");
        }
        if (data.containsKey("account_name_src")) {
            data.remove("account_name_src");
        }
    }
}
