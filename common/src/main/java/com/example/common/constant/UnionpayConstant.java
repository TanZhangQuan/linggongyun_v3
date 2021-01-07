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
    String GATEWAYURL = "http://47.99.58.100:10830/gateway";

    /**
     * 平台公钥
     */
    String PFMPUBKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQClPrAIo8gh9HCMniYWJxxws4KK4pfi4th4Sh8/FLGNUNGZd+o9oMzbzRyXUOs5fsBiAGGBuHGuhi0visTm6m5JYt4uLiNfQP/y/aGxbtYVBCE1BS1PF9MDSsfBqUA58JyOmF/WFjjwR1EpKMbGh2Db/K2wSC+qiR6ZSWDGSs2BNQIDAQAB";

    /**
     * 合作方私钥
     */
    String PRIKEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIFTR+zckPF0h0PKQ2IQD5IGQbaUChkTgHVzbFJjc6U4BJLCAmp+yTTM7jqtEMvAyBmLjhwKU/VEVWnqZCJP69kHUaBRcNeoSO8ES5bjjgFO6uaRI1Fb8TCdrtKfHUeMSJHeQDjvRerx42KT9048J5l9jo6mMG2CkZ0vH/33fj/RAgMBAAECgYBRzkyBGETXd87Youlc7qvqwupug9afZiasJQcwVpigun6qFu9QTkMYk0le9HrbaGcrQYvzUNUrIL6m8Q3GZqfZCo8GHd8sJFzYwxSBd47+ktoT3FIGulQXQDljg8xBfR8ENDIqyrrX6dtxYTj6CZ545uIZqh8JRWfgCpnjSZaEwQJBAMHgzdmK1NgIhpNhJpRm3lkzLf1QCPExvvMb/sda2NQ6dUSvPmG5DYn2Adjnyh1fwos6NpgvcIPXj88mE3558fsCQQCqw2rw/7ykITQsqM4ZGr+NU+NR4xKXcGQzzsc2nMF1XA891lhWL3qsFLsoE2YRY7xvJjMG1hYpmbW4iq0YIPejAkAIkHSnanGShXXkZsM8hPrHd/JNIU7z0J29wXvUtJelcFujyBX6XSFS+dIFEeAkwgkm+1BUEqxmtov8u5La4GonAkBGoKDAoOWC9QDBX+guVfPYHlQs8EAmRqQLEYEvw1H4mmTrbJYIv4Z7We+2uZ9Dnf638hK0xyNPfXW9qA3Dpw8FAkEAuqsoLuPyoOTDBQ1XjajzYFLknVEWClTnGLnV5r1retxr0PLAO8hq1A4fpgmPMKBCzMpzIGDRzjZdiVdJxkJKoA==";

    /**
     * 商户号
     */
    String MERCHNO = "121818722";

    /**
     * 平台帐户账号
     */
    String ACCTNO = "0880200707000000333";

    /**
     * 提现交易回调地址
     */
    String TXCALLBACKADDR = "";

}
