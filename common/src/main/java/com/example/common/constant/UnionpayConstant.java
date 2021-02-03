package com.example.common.constant;

/**
 * 银联配置
 *
 * @author tzq
 * @since 2020/6/2 23:32
 */
public interface UnionpayConstant {

    /**
     * 银联请求地址
     */
    String GATEWAYURL = "http://47.99.58.100:10830/gateway"; //沙箱请求地址
//    String GATEWAYURL = "https://tax.zxszkj.com:1443/gateway"; //正式环境请求地址

    /**
     * 提现到卡异步回调地址
     */
    String TXCALLBACKADDR = "https://lgy-v3.lgyun.com.cn/notice/unionpay/txResult"; //沙箱提现交易回调地址
//    String TXCALLBACKADDR = "https://xmapi.tianbanggroup.com/notice/unionpay/txResult"; //正式环境提现交易回调地址

}
