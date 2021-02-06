package com.example.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author tzq
 * @description 银联支付配置
 * @date 2021-02-06
 */
@Component
@Validated
@ConfigurationProperties(prefix = "spring.unionpay")
public class UnionpayConfig {

    /**
     * 请求URL
     */
    @NotBlank
    private static String gatewayUrl;

    /**
     * 提现到卡异步回调URL
     */
    @NotBlank
    private static String txAsyncNotifyUrl;

    /**
     * SFTP host
     */
    @NotBlank
    private static String sftpHost;

    /**
     * SFTP端口号
     */
    @NotNull
    private static Integer sftpPort;

    /**
     * SFTP用户名
     */
    @NotBlank
    private static String sftpUserName;

    /**
     * SFTP密码
     */
    @NotBlank
    private static String sftpPassword;

    /**
     * SFTP文件下载保存地址
     */
    @NotBlank
    private static String sftpSavePath;

    /**
     * SFTP文件压缩保存地址（SFTP文件下载保存地址的上一个文件夹）
     */
    @NotBlank
    private static String sftpCompressPath;


    public void setGatewayUrl(String gatewayUrl) {
        UnionpayConfig.gatewayUrl = gatewayUrl;
    }

    public void setTxAsyncNotifyUrl(String txAsyncNotifyUrl) {
        UnionpayConfig.txAsyncNotifyUrl = txAsyncNotifyUrl;
    }

    public void setSftpHost(String sftpHost) {
        UnionpayConfig.sftpHost = sftpHost;
    }

    public void setSftpPort(Integer sftpPort) {
        UnionpayConfig.sftpPort = sftpPort;
    }

    public void setSftpUserName(String sftpUserName) {
        UnionpayConfig.sftpUserName = sftpUserName;
    }

    public void setSftpPassword(String sftpPassword) {
        UnionpayConfig.sftpPassword = sftpPassword;
    }

    public void setSftpSavePath(String sftpSavePath) {
        UnionpayConfig.sftpSavePath = sftpSavePath;
    }

    public void setSftpCompressPath(String sftpCompressPath) {
        UnionpayConfig.sftpCompressPath = sftpCompressPath;
    }


    public static String getGatewayUrl() {
        return gatewayUrl;
    }

    public static String getTxAsyncNotifyUrl() {
        return txAsyncNotifyUrl;
    }

    public static String getSftpHost() {
        return sftpHost;
    }

    public static Integer getSftpPort() {
        return sftpPort;
    }

    public static String getSftpUserName() {
        return sftpUserName;
    }

    public static String getSftpPassword() {
        return sftpPassword;
    }

    public static String getSftpSavePath() {
        return sftpSavePath;
    }

    public static String getSftpCompressPath() {
        return sftpCompressPath;
    }

}
