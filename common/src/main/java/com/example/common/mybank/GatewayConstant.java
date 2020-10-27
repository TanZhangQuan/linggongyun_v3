/**
 * 
 */
package com.example.common.mybank;

/**
 * <p>注释</p>
 * @author fjl
 * @version $Id: GatewayConstant.java, v 0.1 2013-11-13 下午4:01:07 fjl Exp $
 */
public interface GatewayConstant {
    /*
     * 连接符
     */
    public static final String and               = "&";
    public static final String eq                = "=";
    public static final String empty             = "";
    /*
     * 字符集
     */
    public static final String charset_iso_latin = "ISO-8859-1";
    public static final String charset_utf_8     = "UTF-8";
    public static final String charset_gbk       = "GBK";
    public static final String charset_gb2312    = "GB2312";
    
    public static final String EXPANDED_NAME_TMP    = ".TMP";
    
    /**
     * 与配置文件中keyStoreName保持一致
     */
    public static final String KEY_STORE_NAME    = "testKeyStore";
}
