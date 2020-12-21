package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WorkerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客总赚钱金额
     */
    private String realMoney ;

    /**
     * 创客头像
     */
    private String headPortraits;

    /**
     * 创客姓名
     */
    private String accountName;
}
