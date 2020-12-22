package com.example.mybatis.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/2
 */
@Data
public class QueryTobeinvoicedDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 平台服务商
     */
    private String platformServiceProvider;

    /**
     * 创建时间1
     */
    private String applicationDateOne;

    /**
     * 创建时间2
     */
    private String applicationDateTwo;

    /**
     * 页码
     */
    private Integer pageNo = 1;

    /**
     * 页大小
     */
    private Integer pageSize = 10;
}
