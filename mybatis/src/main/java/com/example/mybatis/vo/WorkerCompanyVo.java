package com.example.mybatis.vo;

import lombok.Data;

/**
 * @author Jwei
 */
@Data
public class WorkerCompanyVo {

    /**
     * 创客ID
     */
    private String id;
    /**
     * 创客姓名
     */
    private String accountName;
    /**
     * 电话号码
     */
    private String mobileCode;
    /**
     * 身份证号码
     */
    private String idcardCode;
}
