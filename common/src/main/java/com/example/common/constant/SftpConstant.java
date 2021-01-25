package com.example.common.constant;

/**
 * Sftp配置
 *
 * @author tzq
 * @since 2020/6/2 23:32
 */
public interface SftpConstant {

    //共用配置
    // sftp下载保存地址
    String SAVELOCALPATH = "D:/unionpay/sftp";
    // sftp文件压缩地址
    String COMPRESSLOCALPATH = "D:/unionpay";

    //银联sftp配置
    // 银联sftp地址
    String UNIONPAYSFTPHOST = "47.99.58.100";
    // 银联sftp用户名
    String UNIONPAYSFTPUSERNAME = "tax_read";
    // 银联sftp密码
    String UNIONPAYSFTPPASSWORD = "DWFwPe4DgXWxaBPX";

}
