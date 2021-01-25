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
//    String SAVELOCALPATH = "D:/unionpay/sftp"; //本地测试环境sftp下载保存地址
    String SAVELOCALPATH = "D:/unionpay/sftp"; //生产环境sftp下载保存地址
    // sftp文件压缩地址
//    String COMPRESSLOCALPATH = "D:/unionpay"; //本地测试环境sftp文件压缩地址
    String COMPRESSLOCALPATH = "D:/unionpay"; //生产环境sftp文件压缩地址

    //银联sftp配置
    // 银联sftp地址
//    String UNIONPAYSFTPHOST = "47.99.58.100"; //沙箱sftp地址
    String UNIONPAYSFTPHOST = "47.99.58.100"; //正式环境sftp地址
    // 银联sftp用户名
//    String UNIONPAYSFTPUSERNAME = "tax_read"; //沙箱sftp用户名
    String UNIONPAYSFTPUSERNAME = "tax_read"; //正式环境sftp用户名
    // 银联sftp密码
//    String UNIONPAYSFTPPASSWORD = "DWFwPe4DgXWxaBPX"; //沙箱sftp密码
    String UNIONPAYSFTPPASSWORD = "DWFwPe4DgXWxaBPX"; //正式环境sftp密码

}
