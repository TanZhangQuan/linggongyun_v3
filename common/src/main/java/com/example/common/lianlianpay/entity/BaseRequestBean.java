package com.example.common.lianlianpay.entity;

import java.io.Serializable;

/**
 * 基础request bean
 *
 * @author lihp
 * @date 2016-7-16 下午12:30:08
 */
public abstract class BaseRequestBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3882734663326736687L;

    /**
     * 商户编号（非空）
     */
    public String oid_partner;

    /**
     * 签名方式（非空）
     */
    public String sign_type;

    /**
     * 签名 （非空）
     */
    public String sign;

    // 日志追踪号
    public String correlationId;

    // 接口版本号
    public String version = "1.0";

    // 请求IP
    public String requestIp;

    public String getOid_partner() {
        return oid_partner;
    }

    public void setOid_partner(String oid_partner) {
        this.oid_partner = oid_partner;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public abstract String validateLogic();

}
