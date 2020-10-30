/**
 * 
 */
package com.example.common.mybank.util;

import lombok.ToString;

/**
 * <p>注释</p>
 * @author fjl
 * @version $Id: SignData.java, v 0.1 2013-12-19 下午7:40:43 fjl Exp $
 */
@ToString
public class SignData {
    private String  src;

    private String  sign;

    private String  signType;
    
    private String  signLinkStr;

    private boolean success;

    public String getSignLinkStr() {
        return signLinkStr;
    }

    public void setSignLinkStr(String signLinkStr) {
        this.signLinkStr = signLinkStr;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
